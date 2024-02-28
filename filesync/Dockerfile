FROM python:3
WORKDIR /usr/src/app
COPY ./* ./
RUN mkdir -p /usr/src/app/data
RUN ls -lrt /usr/src/app/
RUN pip install --no-cache-dir -r requirements.txt
CMD ["python", "-u", "imdb_data_sync_job.py"]