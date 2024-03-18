import os
import schedule
import time

from clean_up_files import cleanup_files
from job_logger import get_module_logger
from sync_file import sync_data_from_file

log = get_module_logger(__name__)

# TODO: I should just take these in as parameters then pass them through the docker file
job_sync_time = os.environ.get('SYNC_JOB_TIME')
data_file_dir = os.environ.get('DATA_FILE_DIR')
run_sync_on_start = os.environ.get('RUN_SYNC_ON_START')


def create_data_folder_if_none_exists():
    """Set up required data dir"""
    if not os.path.isdir(data_file_dir):
        log.info("Making data folder")
        os.makedirs(data_file_dir)


def job():
    """Main job run
        * Pull all data files from Imdb endpoint
        * Unzip those files
        * Load data in a batched manner
        * Clean up and enrich data
        * Push to the db
        * Clean up old files
    """
    log.info("I'm Starting the imdb sync data job. ")
    log.info("Syncing data tables")
    create_data_folder_if_none_exists()

    data_files = ["title.basics", "title.principals", "name.basics"]

    for data_file in data_files:
        sync_data_from_file(data_file)

    cleanup_files(data_file_dir)
    log.info("I've Finished the imdb sync data job")
    log.info("Next sync run will start at: {job_sync_time} "
             .format(job_sync_time=job_sync_time))


if __name__ == "__main__":

    if not run_sync_on_start:
        log.info("This job will sync the Imdb data every day at: {job_sync_time}"
                 .format(job_sync_time=job_sync_time))

        schedule.every().day.at(job_sync_time).do(job)

    else:
        log.info("Job set to run on start")
        log.info("This job will start immediately, "
                 "then subsequent syncs will be run every day at {job_sync_time}"
                 .format(job_sync_time=job_sync_time))

        job()

        schedule.every().day.at(job_sync_time).do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
