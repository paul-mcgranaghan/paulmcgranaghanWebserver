import os

import pandas
from dotenv import load_dotenv
from sqlalchemy import create_engine, text, Boolean

load_dotenv()
database_url = os.environ.get('DATABASE_URL')


def get_db():
    """Get a db engine by specified data source url"""
    return create_engine(database_url, connect_args={'options': '-csearch_path={}'.format('imdb')},
                         executemany_mode='values_plus_batch')


def get_all_by_ids(datasource_engine, data_set_name, data_ids):
    query = text("SELECT _id FROM " + data_set_name + " WHERE _id IN :values;")
    query = query.bindparams(values=tuple(data_ids))
    return pandas.read_sql(query, datasource_engine)['_id'].tolist()


def insert_as_batch(datasource_engine, data_set_name, dictionary_batch):
    return dictionary_batch.to_sql(data_set_name, datasource_engine, if_exists='append',
                                   index=False, dtype={"isAdult": Boolean}, method='multi')
