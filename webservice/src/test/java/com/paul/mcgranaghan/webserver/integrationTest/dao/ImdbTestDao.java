package com.paul.mcgranaghan.webserver.integrationTest.dao;

import com.paul.mcgranaghan.webserver.dto.NameBasics;
import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ImdbTestDao {

    private static final String INSERT_INTO_NAME_BASIC_TABLE = """
              INSERT INTO public.name_basics
                    ("_id", nconst, "primaryName", "birthYear", "deathYear", "primaryProfession", "knownForTitles")
              VALUES(:_id, :nconst, :primaryName, :birthYear, :deathYear, :primaryProfession, :knownForTitles);                                                             \s
            """;

    private static final String INSERT_INTO_TITLE_BASIC_TABLE = """
            INSERT INTO public.title_basics
                    ("_id", "titleType", "primaryTitle", "originalTitle", "isAdult", "startYear", "endYear", "runtimeMinutes", "genres")
            VALUES (:_id, :titleType, :primaryTitle, :originalTitle, :isAdult, :startYear, :endYear, :runtimeMinutes, :genres);
            """;
    private static final String INSERT_INTO_TITLE_PRINCIPAL_TABLE = """
            INSERT INTO public.title_principals
                    ("_id", "tconst", "ordering", "nconst", "category", "job", "characters")
            VALUES (:_id, :tconst, :ordering, :nconst, :category, :job, :characters);
            """;

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void saveNameBasic(NameBasics nameBasics) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_id", nameBasics._id);
        paramMap.put("nconst", nameBasics.nConst);
        paramMap.put("primaryName", nameBasics.primaryName);
        paramMap.put("birthYear", Integer.getInteger(nameBasics.birthYear));
        paramMap.put("deathYear", Integer.getInteger(nameBasics.deathYear));
        paramMap.put("primaryProfession", nameBasics.primaryProfession);
        paramMap.put("knownForTitles", nameBasics.knownForTitles);

        namedParameterJdbcTemplate.update(INSERT_INTO_NAME_BASIC_TABLE, paramMap);
    }

    public void saveTitleBasic(TitleBasics titleBasic) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_id", titleBasic._id);
        paramMap.put("titleType", titleBasic.titleType);
        paramMap.put("primaryTitle", titleBasic.primaryTitle);
        paramMap.put("originalTitle", titleBasic.originalTitle);
        paramMap.put("isAdult", titleBasic.isAdult() ? 1 : 0);
        paramMap.put("startYear", titleBasic.startYear);
        paramMap.put("endYear", titleBasic.endYear);
        paramMap.put("runtimeMinutes", Integer.valueOf(titleBasic.runtimeMinutes));
        paramMap.put("genres", titleBasic.genres);

        namedParameterJdbcTemplate.update(INSERT_INTO_TITLE_BASIC_TABLE, paramMap);
    }
    public void saveTitlePrinciple(TitlePrinciple titlePrinciple) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_id", titlePrinciple._id);
        paramMap.put("tconst", titlePrinciple.tconst);
        paramMap.put("ordering", titlePrinciple.ordering);
        paramMap.put("nconst", titlePrinciple.nconst);
        paramMap.put("category", titlePrinciple.category);
        paramMap.put("job", titlePrinciple.job);
        paramMap.put("characters", titlePrinciple.characters);

        namedParameterJdbcTemplate.update(INSERT_INTO_TITLE_PRINCIPAL_TABLE, paramMap);
    }
}
