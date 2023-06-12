import pandas
from dataclasses import dataclass

from dto.PrincipleCategory import PrincipleCategory


@dataclass
class TitlePrinciple:
    def __init__(self, _id, t_const, ordering,
                 n_const, category, job, characters):
        self._id: str = _id
        self.t_const: str = t_const
        self.ordering: int = ordering
        self.n_const: str = n_const
        self.category: PrincipleCategory = category
        self.job: str = job
        self.characters: list[str] = characters


def df_to_list(df: pandas.DataFrame) -> list:
    return list(map(lambda x: TitlePrinciple(_id=x[0], t_const=x[1], ordering=x[2],
                                             n_const=x[3], category=x[4], job=x[5], characters=x[6]),
                    df.values.tolist()))
