import csv
import datetime
import gzip
import os
import time
import urllib.request
from re import sub

import numpy
import pandas
from dotenv import load_dotenv
from sqlalchemy import create_engine, text, exc, Boolean

from job_logger import get_module_logger

today = datetime.date.today()
DATE_FORMAT = "%m-%d-%y"
TIME_FORMAT = "hh:MM:ss:sss"

load_dotenv()

database_url = os.environ.get('DATABASE_URL')
data_file_dir = os.environ.get('DATA_FILE_DIR')

log = get_module_logger(__name__)


def sync_data_from_file(data_set_name):
    """Main log flow,  For a specified filename:
        * Get and decompress file
        * Update the database in a batched manner
    """
    compressed_file_name = (data_file_dir + today.strftime(DATE_FORMAT) +
                            "." + data_set_name + ".tsv.gz")
    file_name = today.strftime(DATE_FORMAT) + "." + data_set_name + ".csv"

    download_todays_file_if_not_present(compressed_file_name, data_set_name, file_name)

    working_data_file = decompress_todays_file_if_not_done_already(file_name, compressed_file_name)
    chunk_size = 100000
    insert_count = 0
    processed_count = 0

    with pandas.read_csv(working_data_file, chunksize=chunk_size, low_memory=False,
                         encoding="utf-8", delimiter='\t', quoting=csv.QUOTE_NONE) as reader:
        start_time = datetime.datetime.now()

        log.info("Processing chunks, start time " + start_time.strftime("%I:%M:%S %p"))

        chunk_counter = 1
        for chunk in reader:

            column_names = []

            for column in chunk.columns:
                column_names.append(snake_case(column))

            chunk.columns = column_names

            log.info("Processing chunk number: {chunk_counter}"
                     .format(chunk_counter=chunk_counter))

            chunk = add_id_to_data_chunk(chunk, data_set_name)

            chunk_ids = chunk['_id'].to_list()

            insert_count = insert_count + update_database(get_db(), chunk_ids,
                                                          chunk, data_set_name.replace(".", "_"))
            processed_count += chunk_size
            chunk_counter += 1

        log.info("{processed_count} entry(s) processed and {insert_count} "
                 " new entry(s) inserted into collection: {data_set_name}"
                 .format(processed_count=processed_count, insert_count=insert_count, data_set_name=data_set_name[0]))

        end_time = datetime.datetime.now()
        time_taken = end_time - start_time

        hours, remainder = divmod(time_taken.total_seconds(), 3600)
        minutes, seconds = divmod(remainder, 60)

        log.info("Time taken: {:.0f} hours, {:.0f} minutes, {:.2f} seconds".format(hours, minutes, seconds))


def get_db():
    """Get a db engine by specified data source url"""
    return create_engine(database_url, connect_args={'options': '-csearch_path={}'.format('imdb')},
                         executemany_mode='values_plus_batch')


def snake_case(s):
    """ Replace hyphens with spaces, then apply regular expression substitutions for title case
    conversion and add an underscore between words, finally convert the result to lowercase
    """
    return '_'.join(
        sub('([A-Z][a-z]+)', r' \1',
            sub('([A-Z]+)', r' \1',
                s.replace('-', ' '))).split()).lower()


def download_todays_file_if_not_present(compressed_file_name, data_set_name, file_name):
    """Check if today's specified file exists, and if not download it """
    if (os.path.isfile(compressed_file_name) or
            os.path.isfile(data_file_dir + file_name)):
        log.info("File {data_set_name} already pulled for today, I'm not gonna bother downloading again"
                 .format(data_set_name=data_set_name))

    else:
        log.info("Downloading today's {data_set_name} file"
                 .format(data_set_name=data_set_name))
        assert isinstance(compressed_file_name, object)
        urllib.request.urlretrieve("https://datasets.imdbws.com/" + data_set_name + ".tsv.gz", compressed_file_name)


def decompress_todays_file_if_not_done_already(file_name, compressed_file_name):
    """Check if a specified file has already been decompressed, and if not decompressed"""
    if os.path.isfile(file_name):
        log.info("I've already converted today's % file into a csv, fuck doing it again", file_name)
        return os.open(file_name, flags=os.O_RDONLY)

    log.info("Decompressing {compressed_file_name} file and converting to csv"
             .format(compressed_file_name=compressed_file_name))
    return unzip_data_file(compressed_file_name)


def unzip_data_file(compressed_file_name):
    """Use gzip to unzip and file by file name, return the open file"""
    return gzip.open(compressed_file_name, 'rb')


def add_id_to_data_chunk(chunk, data_set_name):
    """Receives a data chunk of data and enriches with relevant id + data fields.
    I should really convert this into and enricher pattern"""
    # TODO: These id's don't really work, they should function as a key,
    #  but still getting duplicates
    # Actually it might just be the data that has duplicates
    if data_set_name == "title.principals":
        chunk = add_principal_id(chunk).dropna(subset=['nconst', 'tconst', 'ordering'])
    elif data_set_name == "name.basics":
        chunk = add_name_id(chunk).dropna(subset=['nconst', 'primary_name'])
    else:
        chunk = chunk.rename(columns={"tconst": '_id'}).dropna(subset=['_id'])
        chunk = convert_number_to_bool(chunk)
    return chunk


def add_principal_id(chunk):
    """Receives Title principal data chunk and returns chunk with unique id for rows."""
    chunk['_id'] = chunk['nconst'] + '-' + chunk['tconst'] + '-' + chunk['ordering'].astype(str)
    return chunk


def add_name_id(chunk):
    """Receives Name basics data chunk and returns chunk with unique id for rows."""
    chunk['_id'] = chunk['nconst'] + '-' + chunk['primary_name'].str.replace(" ", "")
    return chunk


def convert_number_to_bool(chunk):
    """Set 0 and 1 to False and True"""
    chunk['is_adult'] = chunk['is_adult'].astype(bool)
    return chunk


def update_database(db_engine, data_ids, dictionary_batch: pandas.DataFrame, data_set_name):
    """Look up the ids to process in the database, see if they're already there."""

    query = text("SELECT _id FROM " + data_set_name + " WHERE _id IN :values;")

    query = query.bindparams(values=tuple(data_ids))
    present_ids = pandas.read_sql(query, db_engine)['_id'].tolist()

    if present_ids is not None:
        dictionary_batch = dictionary_batch[~dictionary_batch['_id'].isin(present_ids)]

    if len(dictionary_batch) > 0:
        log.info("Processing batch of {dictionary_batch} new record(s)."
                 .format(dictionary_batch=len(dictionary_batch)))
        dictionary_batch = dictionary_batch.replace("\\N", None)

        try:
            log.info("New records to add, inserting {dictionary_batch} record(s)"
                     .format(dictionary_batch=len(dictionary_batch)))
            return dictionary_batch.to_sql(data_set_name, db_engine, if_exists='append',
                                           index=False, dtype={"isAdult": Boolean}, method='multi')
        except exc.DataError as e:
            # TODO: handle insert of non failing rows in batch
            log.error("Batch insert failed due to DataError,"
                      " inserting individually and skipping bad record: {bad_record}".format(bad_record=e.args[0]))
            return insert_in_10_chunks(dictionary_batch, data_set_name, db_engine)
    else:
        return 0


def insert_in_10_chunks(dictionary_batch, data_set_name, db_engine):
    """For if the batch insert fails insert in smaller chunks"""
    # This array split gives empty lists
    batch_chunks = numpy.array_split(dictionary_batch, 10)
    batch_chunks = list(filter(lambda x: not x.empty, batch_chunks))
    rows_inserted = 0
    for row in batch_chunks:
        try:
            row.to_sql(data_set_name, db_engine, if_exists='append', index=False)
            rows_inserted = rows_inserted + len(row)
        except exc.DataError as e:
            log.error(e)
            rows_processed = insert_individually(row, data_set_name, db_engine)
            rows_inserted = rows_inserted + len(rows_processed) - 1

            # Drop processed values from batch,
            # TODO: need to think about if/how to get this row reprocessed
            row = row[~row._id.str.contains('|'.join(rows_processed))]
            # Drop by _id list
    return rows_inserted


def insert_individually(dictionary_batch, data_set_name, db_engine):
    """Insert data one at a time, and error out the failing row"""
    # TODO: How to remove inserted and failed from chunk and insert again?
    rows_inserted = 0
    rows_processed = []
    for row in dictionary_batch.iterrows():
        try:
            pandas.DataFrame(row[1]).T.to_sql(data_set_name, db_engine,
                                              if_exists='append', index=False)
            rows_inserted = rows_inserted + 1
            rows_processed.append(row[1]['_id'])

        except exc.DataError as e:
            log.error("Failed to Insert row _id: {bad_row}"
                      .format(bad_row=row[1]['_id']))
            log.error("Row fail exception: {exception}"
                      .format(exception=e))
            rows_processed.append(row[1]['_id'])
            return rows_processed

    return rows_processed
