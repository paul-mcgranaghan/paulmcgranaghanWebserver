import os

from sqlalchemy import create_engine, text, exc

database_url = os.environ.get('DATABASE_URL')


def cleanup_database():
    """Cleanup database should removed duplicates or data noise """
    db_engine = get_db()
    # db_engine.


def get_db():
    """Get db engine"""
    return create_engine(database_url)

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
