import schedule
import time

from CleanUpFiles import cleanup_files
from JobLogger import get_module_logger
from SyncFile import sync_data_from_file
from filesync.dto.NameBasics import df_to_list as df_name
from filesync.dto.TitleBasics import df_to_list as df_title
from filesync.dto.TitlePrinciple import TitlePrinciple

SYNC_JOB_TIME = "16:23:00"

log = get_module_logger(__name__)

if __name__ == "__main__":
    log.info("I'm Starting the imdb sync data job. "
             "This job will sync the Imdb data every day at " + SYNC_JOB_TIME)


def job():
    log.info("Syncing data tables")

    sync_data_from_file("title.basics", "./data/", "tconst", df_title)
    sync_data_from_file("title.principals", "./data/", "nconst", TitlePrinciple)
    sync_data_from_file("name.basics", "./data/", "nconst", df_name)
    cleanup_files("./data/")
    log.info("I've Finished the imdb sync data job")


schedule.every().day.at(SYNC_JOB_TIME).do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
