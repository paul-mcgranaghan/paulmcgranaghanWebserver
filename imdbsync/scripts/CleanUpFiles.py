import os
import shutil
import time
from pprint import pprint

two_days_ago = time.time() - (2 * 86400)


def cleanup_files(file_location):
    print("Starting clean up job for location: " + file_location)

    pprint("Checking for files older than date: " + str(two_days_ago))
    for file in os.listdir(file_location):
        path = os.path.join(file_location, file)

        if os.stat(path).st_mtime <= two_days_ago:
            pprint("File to delete found: " + path)
            if os.path.isfile(path):
                try:

                    os.remove(path)
                    pprint("File deleted: " + path)

                except:
                    pprint("Could not remove file:" + file)

            else:
                try:
                    shutil.rmtree(path)

                except:
                    pprint("Could not remove directory:" + file)
