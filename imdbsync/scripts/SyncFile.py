import datetime
import gzip
import json
import os
import time
import urllib.request
from pprint import pprint

import pandas
import pymongo

today = datetime.date.today()
date_format = "%m-%d-%y"
time_format = "hh:MM:ss:sss"


def sync_data_from_file(data_set_name, data_location, data_key):
    compressed_file_name = data_location + today.strftime(date_format) + "." + data_set_name + ".tsv.gz"
    file_name = today.strftime(date_format) + "." + data_set_name + ".csv"

    download_todays_file_if_not_present(compressed_file_name, data_location, data_set_name, file_name)

    working_data_file = decompress_todays_file_if_not_done_already(data_location, file_name, compressed_file_name)

    with pandas.read_csv(working_data_file, chunksize=1000000, low_memory=False, encoding="utf-8",
                         delimiter='\t') as reader:
        collection = get_db(data_set_name)
        start_time = time.time()
        pprint("Processing chunks")

        for chunk in reader:
            chunk = add_id_to_data_chunk(chunk, data_key, data_set_name)

            update_database(collection, json.loads(chunk.to_json(orient='records')))

        end_time = time.time()
        time_taken = end_time - start_time
        pprint("Done processing file in :" + str(time_taken))


def add_id_to_data_chunk(chunk, data_key, data_set_name):
    if data_set_name == "title.principals":
        chunk = add_principal_id(chunk)
    elif data_set_name == "name.basics":
        chunk = add_name_id(chunk)
    else:
        chunk = chunk.rename(columns={data_key: '_id'})
    return chunk


def decompress_todays_file_if_not_done_already(data_location, file_name, compressed_file_name):
    if os.path.isfile(data_location + file_name):
        pprint("I've already converted today's " + file_name + " file into a csv, fuck doing it again")
        return os.open(data_location + file_name, flags=os.O_RDONLY)
    else:
        pprint("Decompressing " + compressed_file_name + " file and converting to csv")
        return unzip_data_file(data_location, compressed_file_name)


def download_todays_file_if_not_present(compressed_file_name, data_location, data_set_name, file_name):
    if os.path.isfile(compressed_file_name) or os.path.isfile(data_location + file_name):
        pprint("File " + data_set_name + " already pulled for today, I'm not gonna bother downloading again")

    else:
        pprint("Downloading today's " + data_set_name + " file")
        urllib.request.urlretrieve("https://datasets.imdbws.com/" + data_set_name + ".tsv.gz", compressed_file_name)


def unzip_data_file(data_location, compressed_file_name):
    return gzip.open(data_location + compressed_file_name, 'rb')


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
    my_client = pymongo.MongoClient('mongodb://user:pass@host.docker.internal:2717/')
    database = my_client.get_database('imdb')

    pprint("Cleaning out database before load")
    database.get_collection(data_set).delete_many({})
    return database.get_collection(data_set)


def update_database(collection, dictionary_batch):
    # TODO: work out the update or add if not present
    collection.insert_many(dictionary_batch, ordered=False)
    # collection.update_many(dictionary_batch, upsert=True)
