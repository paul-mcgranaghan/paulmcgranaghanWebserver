import schedule
import time
from SyncFile import sync_data_from_file
from CleanUpFiles import cleanup_files
from pprint import pprint


def job():
    pprint("I'm Starting the imdb sync data job")
    sync_data_from_file("title.basics", "../data/", "tconst")
    sync_data_from_file("title.principals", "../data/", "nconst")
    sync_data_from_file("name.basics", "../data/", "nconst")
    cleanup_files("./")
    pprint("I've Finished the imdb sync data job")


# schedule.every(20).seconds.do(job)
# schedule.every(5).minutes.do(job)
schedule.every().day.at("08:30").do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
