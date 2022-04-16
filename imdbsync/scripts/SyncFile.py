import urllib.request
import gzip
import os
import pandas
import csv
from datetime import date
import pymongo
from pprint import pprint

today = date.today()
date_format = "%m-%d-%y"


def sync_file(data_set, data_location):
    compressed_file_name = data_location + today.strftime(date_format) + "." + data_set + ".tsv.gz"
    file_name = today.strftime(date_format) + "." + data_set + ".csv"
    log_out = "Let's see the head of the file"

    if os.path.isfile(compressed_file_name) or os.path.isfile(data_location + file_name):
        pprint("File " + data_set + " already pulled for today, I'm not gonna bother downloading again")

    else:
        pprint("Downloading today's " + data_set + " file")
        urllib.request.urlretrieve("https://datasets.imdbws.com/" + data_set + ".tsv.gz", compressed_file_name)

    if os.path.isfile(data_location + file_name):
        pprint("I've already converted today's " + data_set + " file into a csv, fuck doing it again")
    else:
        pprint("Decompressing " + compressed_file_name + " file and converting to csv")
        with gzip.open(data_location + compressed_file_name, 'rb') as f_in:
            f_in_table = pandas.read_table(f_in, sep='\t', low_memory=False)
            f_in_table.to_csv(data_location + file_name, index=False)

    with open(data_location + file_name) as data_in:
        # TODO: Find a way to set the tconst value to _id so mongo uses it as the key
        pprint("Reading " + data_set + " file csv in...")
        dict_reader = csv.DictReader(data_in)

        for row in dict_reader:
            update_database(data_set, row)


def update_database(data_set, dictionary):
    conn = pymongo.MongoClient('mongodb://user:pass@localhost:2717/')

    collection = conn.get_database('imdb').get_collection(data_set)
    collection.update_one(dictionary)
    # collection.insert_many(dictionary)
