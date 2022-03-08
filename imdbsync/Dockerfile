FROM python:3
WORKDIR /usr/src/app
COPY ./* ./
RUN ls -lrt /usr/src/app/
RUN pip install --no-cache-dir -r requirements.txt
CMD ["python", "SyncTitles.py"]