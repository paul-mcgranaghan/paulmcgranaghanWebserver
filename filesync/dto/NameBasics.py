import pandas
from dataclasses import dataclass

from filesync.dto.Profession import Profession


@dataclass
class NameBasics:
    def __init__(self, _id, n_const, primary_name, birth_year, death_year,
                 primary_profession, known_for_titles):
        self._id: str = _id
        self.n_const: str = n_const
        self.primary_name: str = primary_name
        self.birth_year: str = birth_year
        self.death_year: str = death_year
        self.primary_profession: set[Profession] = primary_profession
        self.known_for_titles: set[str] = known_for_titles


def df_to_list(df: pandas.DataFrame) -> list:
    return list(map(lambda x: NameBasics(_id=x[0], n_const=x[1], primary_name=x[2],
                                         birth_year=x[3], death_year=x[4],
                                         primary_profession=x[5],
                                         known_for_titles=x[6]), df.values.tolist()))
