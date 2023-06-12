import time
import os
import schedule

from CleanUpFiles import cleanup_files
from JobLogger import get_module_logger
from SyncFile import sync_data_from_file

SYNC_JOB_TIME = '99:99:99'

log = get_module_logger(__name__)

if __name__ == "__main__":
    log.info("I'm Starting the imdb sync data job. "
             "This job will sync the Imdb data every day at " + SYNC_JOB_TIME)


def create_data_folder_if_none_exists():
    if not os.path.isdir(
            "./data"):
        log.info("Making data file")
        os.mkdir("./data")


def job():
    log.info("Syncing data tables")
    create_data_folder_if_none_exists()
    sync_data_from_file("title.basics")
    sync_data_from_file("title.principals")
    sync_data_from_file("name.basics")
    cleanup_files("./data/")
    log.info("I've Finished the imdb sync data job")


if SYNC_JOB_TIME != '99:99:99':
    schedule.every().day.at(SYNC_JOB_TIME).do(job)
else:
    job()

while True:
    schedule.run_pending()
    time.sleep(1)
