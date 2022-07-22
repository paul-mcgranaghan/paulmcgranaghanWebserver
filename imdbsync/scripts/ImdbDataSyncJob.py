import schedule
import time
from SyncFile import sync_data_from_file
from CleanUpFiles import cleanup_files
from JobLogger import get_module_logger

SYNC_JOB_TIME = "11:43:00"


if __name__ == "__main__":
    get_module_logger(__name__).info("I'm Starting the imdb sync data job\n"
                                     "This job will sync the Imdb data every day at " + SYNC_JOB_TIME)


def job():
    get_module_logger(__name__).info("Syncing data tables")

    # Local
    # sync_data_from_file("title.basics", "../data/", "tconst")
    # sync_data_from_file("title.principals", "../data/", "nconst")
    # sync_data_from_file("name.basics", "../data/", "nconst")

    # Docker
    sync_data_from_file("title.basics", "./data/", "tconst")
    sync_data_from_file("title.principals", "./data/", "nconst")
    sync_data_from_file("name.basics", "./data/", "nconst")
    cleanup_files("./data/")
    get_module_logger(__name__).info("I've Finished the imdb sync data job")


#schedule.every(20).seconds.do(job)
schedule.every(3).minutes.do(job)
#schedule.every().day.at(SYNC_JOB_TIME).do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
