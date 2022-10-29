import datetime
import gzip
import os
import time
import urllib.request

import pandas
from sqlalchemy import create_engine, text, exc

from JobLogger import get_module_logger

today = datetime.date.today()
date_format = "%m-%d-%y"
time_format = "hh:MM:ss:sss"

log = get_module_logger(__name__)


def sync_data_from_file(data_set_name):
    compressed_file_name = "./data/" + today.strftime(date_format) + "." + data_set_name + ".tsv.gz"
    file_name = today.strftime(date_format) + "." + data_set_name + ".csv"

    download_todays_file_if_not_present(compressed_file_name, data_set_name, file_name)

    working_data_file = decompress_todays_file_if_not_done_already(file_name, compressed_file_name)
    chunk_size = 100000
    insert_count = 0
    processed_count = 0

    with pandas.read_csv(working_data_file, chunksize=chunk_size, low_memory=False, encoding="utf-8",
                         delimiter='\t') as reader:
        db_engine = get_db()
        start_time = time.time()
        log.info("Processing chunks")
        chunk_counter = 1
        for chunk in reader:
            log.info("Processing chunk number: " + str(chunk_counter))
            chunk = add_id_to_data_chunk(chunk, data_set_name)

            chunk_ids = chunk['_id'].to_list()

            insert_count = insert_count + update_database(db_engine, chunk_ids,
                                                          chunk, data_set_name.replace(".", "_"))
            processed_count = processed_count + chunk_size
            chunk_counter = chunk_counter + 1

        end_time = time.time()
        time_taken = end_time - start_time
        log.info("Done processing file in :" + str(time_taken))
        log.info(str(processed_count) + ' entry(s) processed and ' + str(insert_count) +
                 ' new entry(s) inserted into collection: ' + data_set_name)


def add_id_to_data_chunk(chunk, data_set_name):
    if data_set_name == "title.principals":
        chunk = add_principal_id(chunk).dropna(subset=['nconst', 'tconst', 'ordering'])
    elif data_set_name == "name.basics":
        chunk = add_name_id(chunk).dropna(subset=['nconst', 'primaryName'])
    else:
        chunk = chunk.rename(columns={"tconst": '_id'}).dropna(subset=['_id'])
    return chunk


def decompress_todays_file_if_not_done_already(file_name, compressed_file_name):
    if os.path.isfile(file_name):
        log.info("I've already converted today's " + file_name + " file into a csv, fuck doing it again")
        return os.open(file_name, flags=os.O_RDONLY)
    else:
        log.info("Decompressing " + compressed_file_name + " file and converting to csv")
        return unzip_data_file(compressed_file_name)


def download_todays_file_if_not_present(compressed_file_name, data_set_name, file_name):
    if os.path.isfile(compressed_file_name) or os.path.isfile("./data/" + file_name):
        log.info("File " + data_set_name + " already pulled for today, I'm not gonna bother downloading again")

    else:
        log.info("Downloading today's " + data_set_name + " file")
        urllib.request.urlretrieve("https://datasets.imdbws.com/" + data_set_name + ".tsv.gz", compressed_file_name)


def unzip_data_file(compressed_file_name):
    return gzip.open(compressed_file_name, 'rb')


def add_principal_id(chunk):
    chunk['_id'] = chunk['nconst'] + '-' + chunk['tconst'] + '-' + chunk['ordering'].astype(str)
    return chunk


def add_name_id(chunk):
    chunk['_id'] = chunk['nconst'] + '-' + chunk['primaryName'].str.replace(" ", "")
    return chunk


def get_db():
    return create_engine('postgresql+psycopg2://postgres:Pa22word@localhost:5432/postgres')


def update_database(db_engine, data_ids, dictionary_batch: pandas.DataFrame, data_set_name):
    # Look up the ids to process in the database, see if they're already there

    query = text("SELECT _id FROM " + data_set_name + " WHERE _id IN :values;")

    query = query.bindparams(values=tuple(data_ids))
    present_ids = pandas.read_sql(query, db_engine)['_id'].tolist()

    if present_ids is not None:
        dictionary_batch = dictionary_batch[~dictionary_batch['_id'].isin(present_ids)]

    if len(dictionary_batch) > 0:
        log.info("New records to add, inserting " + str(len(dictionary_batch)) + " record(s)")
        dictionary_batch = dictionary_batch.replace("\\N", None)
        try:
            return dictionary_batch.to_sql(data_set_name, db_engine, if_exists='append', index=False, )
        except exc.DataError as e:
            # TODO: handle insert of non failing rows in batch
            log.error("Batch insert failed due to DataError, dropping failed record : " + e.args[0])
            return 0
    else:
        return 0


def get_cursor(collection, data_ids):
    return collection.find({'_id': {'$in': data_ids}})


def insert_individually(dictionary_batch, data_set_name, db_engine):
    for row in dictionary_batch.iterrows():
        try:
            pandas.DataFrame(row[1]).T.to_sql(data_set_name, db_engine, if_exists='append', index=False)
        except exc.DataError as e:
            log.error("ERROR Row entry failed thrown exception: ")
            log.error(e)
