import schedule
import time
from SyncFile import sync_file
from CleanUpFiles import cleanupfiles
from pprint import pprint


def job():
    pprint("I'm Starting the imdb sync data job")
    sync_file("title.basics", "../data/")
    #sync_file("title.principals", "../data/")
    #sync_file("name.basics", "../data/")
    cleanupfiles("../data/")
    pprint("I've Finished the imdb sync data job")


schedule.every(20).seconds.do(job)
#schedule.every(5).minutes.do(job)
schedule.every().day.at("08:30").do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
