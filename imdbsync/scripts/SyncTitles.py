import schedule
import time
from SyncFile import syncfile
from CleanUpFiles import cleanupfiles


def job():
    print("I'm Starting the imdb sync data job")
    syncfile("title.basics")
    syncfile("title.principals")
    syncfile("title.name")
    cleanupfiles()
    print("I'm Finished the imdb sync data job")


schedule.every(5).minutes.do(job)
schedule.every().day.at("07:00").do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
