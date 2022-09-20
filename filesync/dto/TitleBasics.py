import pandas

from filesync.dto.Genres import Genres


# @dataclass
class TitleBasics:
    def __init__(self, _id, title_type, primary_title, original_title, is_adult,
                 start_year, end_year, runtime_minutes, genres):
        self._id: str = _id
        self.title_type: str = title_type
        self.primary_title: str = primary_title
        self.original_title: str = original_title
        self.is_adult: bool = is_adult
        self.start_year: str = start_year
        self.end_year: str = end_year
        self.runtime_minutes: str = runtime_minutes
        self.genres: set[Genres] = genres


def df_to_list(df: pandas.DataFrame) -> list:
    return list(map(lambda x: TitleBasics(_id=x[0], title_type=x[1], primary_title=x[2],
                                          original_title=x[3], is_adult=x[4], start_year=x[5],
                                          end_year=x[6], runtime_minutes=x[7], genres=x[8]), df.values.tolist()))
