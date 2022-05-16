import urllib.request
import gzip
import os
import pandas
import csv
from datetime import date
import pymongo
import re
import json
from pprint import pprint

today = date.today()
date_format = "%m-%d-%y"


def sync_file(data_set_name, data_location, data_key):
    compressed_file_name = data_location + today.strftime(date_format) + "." + data_set_name + ".tsv.gz"
    file_name = today.strftime(date_format) + "." + data_set_name + ".csv"
    log_out = "Let's see the head of the file"

    if os.path.isfile(compressed_file_name) or os.path.isfile(data_location + file_name):
        pprint("File " + data_set_name + " already pulled for today, I'm not gonna bother downloading again")

    else:
        pprint("Downloading today's " + data_set_name + " file")
        urllib.request.urlretrieve("https://datasets.imdbws.com/" + data_set_name + ".tsv.gz", compressed_file_name)

    if os.path.isfile(data_location + file_name):
        pprint("I've already converted today's " + data_set_name + " file into a csv, fuck doing it again")
    else:
        pprint("Decompressing " + compressed_file_name + " file and converting to csv")
        f_in = unzip_data_file(data_location, compressed_file_name)
        # TODO: Work out batch optimization
        chunk_size = 10 ** 4
        #chunk_size = 50
        with pandas.read_csv(f_in, chunksize=chunk_size, low_memory=False, encoding="utf-8", delimiter='\t') as reader:
            for chunk in reader:
                if data_set_name == "title.principals":
                    chunk = add_principal_id(chunk)
                elif data_set_name == "name.basics":
                    chunk = add_name_id(chunk)
                else:
                    chunk = chunk.rename(columns={data_key: '_id'})
                update_database(data_set_name, json.loads(chunk.to_json(orient='records')))


def unzip_data_file(data_location, compressed_file_name):
    return gzip.open(data_location + compressed_file_name, 'rb')


def add_principal_id(chunk):
    chunk['_id'] = chunk['nconst'] + '-' + chunk['tconst'] + '-' + chunk['ordering'].astype(str)
    return chunk


def add_name_id(chunk):
    chunk['_id'] = chunk['nconst'] + '-' + chunk['primaryName'].str.replace(" ", "") + '-' + chunk['birthYear'].astype(str)
    return chunk


def update_database(data_set, dictionary_batch):
    # TODO: environment variables
    # my_client = pymongo.MongoClient('mongodb://user:pass@localhost:2717/')
    my_client = pymongo.MongoClient('mongodb://user:pass@host.docker.internal:2717/')

    collection = my_client.get_database('imdb').get_collection(data_set)
    # TODO: work out the update or add if not present
    collection.insert_many(dictionary_batch, ordered=False)
    # collection.update_many(dictionary_batch, upsert=True)
