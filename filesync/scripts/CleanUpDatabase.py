from sqlalchemy import create_engine, text, exc


def cleanup_database():
    db_engine = get_db()
    # db_engine.


def get_db():
    return create_engine('postgresql+psycopg2://postgres:Pa22word@host.docker.internal:5432/postgres')

# with unused_pricipals AS(
# select tb."_id", tp."_id"
# from title_principals tp
# right outer join title_basics tb on tb."_id"  = tp.tconst
# where tp."_id"  is null)
# select count(*)
# from unused_pricipals


# delete from title_basics tb
# where 1=1
# and "titleType" = 'tvEpisode'
# and ("primaryTitle" like 'Episode #%' or "primaryTitle" like 'Episode dated %');

# then reorg?? http://ossc-db.github.io/pg_reorg/pg_reorg.html
