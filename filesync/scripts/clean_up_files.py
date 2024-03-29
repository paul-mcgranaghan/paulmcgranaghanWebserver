"""Clean up file step"""
import os
import time
from job_logger import get_module_logger

two_days_ago = time.time() - (2 * 86400)
two_days_ago_string = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(two_days_ago))

log = get_module_logger(__name__)


def cleanup_files(file_location):
    """Clean up old files from a specified location"""
    log.info("Starting clean up job for location: {file_location}"
             .format(file_location=file_location))
    log.info("Checking for files older than date: {two_days_ago_string}"
             .format(two_days_ago_string=two_days_ago_string))

    if data_dir_has_files(file_location):

        for file in os.listdir(file_location):
            path = os.path.join(file_location, file)
            delete_old_files(file, path)


def data_dir_has_files(file_location):
    """Check if specified dir contains any files."""
    return len(os.listdir(file_location)) > 0


def delete_old_files(file, path):
    """Try to delete files more than 2 days old."""
    if os.stat(path).st_mtime <= two_days_ago:
        log.info("File to delete found: " + path)
        if os.path.isfile(path):

            try:
                os.remove(path)
                log.info("File deleted: " + path)

            except IOError as exc:
                raise IOError("Could not remove file:" + file) from exc
