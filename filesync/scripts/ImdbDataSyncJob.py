import time

import schedule

from CleanUpFiles import cleanup_files
from JobLogger import get_module_logger
from SyncFile import sync_data_from_file

SYNC_JOB_TIME = "23:25:00"

log = get_module_logger(__name__)

if __name__ == "__main__":
    log.info("I'm Starting the imdb sync data job. "
             "This job will sync the Imdb data every day at " + SYNC_JOB_TIME)


def job():
    log.info("Syncing data tables")

    # sync_data_from_file("title.basics", "./data/", "tconst")
    # sync_data_from_file("title.principals", "./data/", "nconst")
    sync_data_from_file("name.basics", "./data/", "nconst", )
    cleanup_files("./data/")
    log.info("I've Finished the imdb sync data job")


schedule.every().day.at(SYNC_JOB_TIME).do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
