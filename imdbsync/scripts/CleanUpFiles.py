import os
import shutil
import time

two_days_ago = time.time() - (2 * 86400)
root = "./"


def cleanupfiles():
    for i in os.listdir(root):
        path = os.path.join(root, i)

        if os.stat(path).st_mtime <= two_days_ago:
            if os.path.isfile(path):
                try:
                    os.remove(path)

                except:
                    print("Could not remove file:", i)

            else:
                try:
                    shutil.rmtree(path)

                except:
                    print("Could not remove directory:", i)
