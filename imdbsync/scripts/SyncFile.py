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


def sync_file(data_set_name, data_location):
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
        chunksize = 10 ** 4
        with pandas.read_csv(f_in, chunksize=chunksize, low_memory=False, encoding="utf-8", delimiter='\t') as reader:
            for chunk in reader:
                chunk = chunk.rename(columns={'tconst': '_id'})
                update_database(data_set_name, json.loads(chunk.to_json(orient='records')))


def unzip_data_file(data_location, compressed_file_name):
    return gzip.open(data_location + compressed_file_name, 'rb')


def update_database(data_set, dictionary_batch):
    my_client = pymongo.MongoClient('mongodb://user:pass@localhost:2717/')

    collection = my_client.get_database('imdb').get_collection(data_set)
    collection.insert_many(dictionary_batch)

