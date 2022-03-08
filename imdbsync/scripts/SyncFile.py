import urllib.request
import gzip
import os
import pandas
from datetime import date

today = date.today()
date_format = "%m-%d-%y"


def syncfile(data_set):
    compressed_file_name = j + "." + data_set + ".tsv.gz"
    file_name = today.strftime(date_format) + "." + data_set + ".csv"
    log_out = "Let's see the head of the file"

    if os.path.isfile('./' + compressed_file_name) or os.path.isfile('./' + file_name):
        print(data_set + " file already pulled for today, I'm not gonna bother downloading again")

    else:
        print("Downloading today's " + data_set + " file")
        urllib.request.urlretrieve("https://datasets.imdbws.com/" + data_set + ".tsv.gz", compressed_file_name)

    if os.path.isfile('./' + file_name):
        print("I've already converted today's " + data_set + " file into a csv, fuck doing it again")
    else:
        print("Decompressing " + data_set + " file and converting to csv")
        with gzip.open('./' + compressed_file_name, 'rb') as f_in:
            f_in_table = pandas.read_table(f_in, sep='\t', low_memory=False)
            f_in_table.to_csv(file_name, index=False)

    with open('./' + file_name, 'rb') as date_in:
        print("reading " + data_set + " file csv in...")
        data = pandas.read_csv(date_in, low_memory=False)

    print(log_out)
    print(data.head())
