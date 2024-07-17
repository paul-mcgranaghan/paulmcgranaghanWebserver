import json
import os

import pymongo
from dotenv import load_dotenv
from job_logger import get_module_logger

load_dotenv()

database_url = os.environ.get('MONGO_DB_URL')

log = get_module_logger(__name__)


def get_db(data_set):
    my_client = pymongo.MongoClient(database_url)
    database = my_client.get_database('imdb')

    return database.get_collection(data_set)


def get_all_by_ids(datasource_engine, data_ids):
    list(datasource_engine.find({'_id': {'$in': data_ids}}, '_id'))


def insert_as_batch(datasource_engine, dictionary_batch):
    to_enter_as_json = json.loads(dictionary_batch.to_json(orient='records'))

    if len(to_enter_as_json) > 0:
        log.info("New records to add, inserting " + str(len(to_enter_as_json)) + " record(s)")
        datasource_engine.insert_many(to_enter_as_json, ordered=False)
        return len(to_enter_as_json)
    else:
        return 0
