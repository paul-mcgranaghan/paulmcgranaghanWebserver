import datetime
import gzip
import json
import os
import time
import urllib.request

import pandas
import pymongo

from JobLogger import get_module_logger

today = datetime.date.today()
date_format = "%m-%d-%y"
time_format = "hh:MM:ss:sss"

log = get_module_logger(__name__)


def sync_data_from_file(data_set_name, data_location, data_key):
    compressed_file_name = data_location + today.strftime(date_format) + "." + data_set_name + ".tsv.gz"
    file_name = today.strftime(date_format) + "." + data_set_name + ".csv"

    download_todays_file_if_not_present(compressed_file_name, data_location, data_set_name, file_name)

    working_data_file = decompress_todays_file_if_not_done_already(file_name, compressed_file_name)

    chunk_size = 100000
    insert_count = 0
    processed_count = 0

    with pandas.read_csv(working_data_file, chunksize=chunk_size, low_memory=False, encoding="utf-8",
                         delimiter='\t') as reader:
        collection = get_db(data_set_name)
        start_time = time.time()
        log.info("Processing chunks")
        i = 1
        for chunk in reader:
            log.info("Processing chunk number: " + str(i))
            chunk = add_id_to_data_chunk(chunk, data_key, data_set_name)
            chunk_ids = chunk['_id'].to_list()

            insert_count = insert_count + update_database(collection, chunk_ids,
                                                          chunk)
            processed_count = processed_count + chunk_size
            i = i + 1

        end_time = time.time()
        time_taken = end_time - start_time
        log.info("Done processing file in :" + str(time_taken))
        log.info(str(processed_count) + ' entry(s) processed and ' + str(insert_count) +
                 ' new entry(s) inserted into collection: ' + data_set_name)


def add_id_to_data_chunk(chunk, data_key, data_set_name):
    if data_set_name == "title.principals":
        chunk = add_principal_id(chunk)
    elif data_set_name == "name.basics":
        chunk = add_name_id(chunk)
    else:
        chunk = chunk.rename(columns={data_key: '_id'})
    return chunk


def decompress_todays_file_if_not_done_already(file_name, compressed_file_name):
    if os.path.isfile(file_name):
        log.info("I've already converted today's " + file_name + " file into a csv, fuck doing it again")
        return os.open(file_name, flags=os.O_RDONLY)
    else:
        log.info("Decompressing " + compressed_file_name + " file and converting to csv")
        return unzip_data_file(compressed_file_name)


def download_todays_file_if_not_present(compressed_file_name, data_location, data_set_name, file_name):
    if os.path.isfile(compressed_file_name) or os.path.isfile(data_location + file_name):
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
    chunk['_id'] = chunk['nconst'] + '-' + chunk['primaryName'].str.replace(" ", "") + '-' + chunk['birthYear'].astype(
        str)
    return chunk


def get_db(data_set):
    # TODO: environment variables
    # my_client = pymongo.MongoClient('mongodb://user:pass@localhost:2717/')
    my_client = pymongo.MongoClient('mongodb://host.docker.internal:27017/')
    database = my_client.get_database('imdb')

    return database.get_collection(data_set)


def update_database(collection, data_ids, dictionary_batch):
    # Look up the ids to process in the database, see if they're already there
    present_ids = list(collection.find({'_id': {'$in': data_ids}}, '_id'))
    # Convert the above list of dicts {'_id': 'tt000000001'} into a list of values ['tt000000001']
    present_ids_to_list = [d['_id'] for d in present_ids]

    # ~ means NOT, df = df[column-name] is in clause (list of values)
    dictionary_batch = dictionary_batch[~dictionary_batch['_id'].isin(present_ids_to_list)]

    # Convert Dataframe to json, so I can put it in the database
    to_enter_as_json = json.loads(dictionary_batch.to_json(orient='records'))

    if len(to_enter_as_json) > 0:
        log.info("New records to add, inserting " + str(len(to_enter_as_json)) + " record(s)")
        collection.insert_many(to_enter_as_json, ordered=False)
        return len(to_enter_as_json)
    else:
        return 0


def get_cursor(collection, data_ids):
    return collection.find({'_id': {'$in': data_ids}})
