package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.NameBasics;
import com.paul.mcgranaghan.webserver.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class NameBasicDao {

    private final static String GET_ACTOR_BY_NAME = """
                        SELECT _id, nconst, primaryName, birthYear, deathYear, primaryProfession, knownForTitles 
                        FROM name_basics
                        WHERE primaryName = :primaryName
            """;
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NameBasics findByName(String primaryName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("primaryName", primaryName);

        try {
            return namedParameterJdbcTemplate.queryForObject(GET_ACTOR_BY_NAME, paramMap, NameBasics.class);
        } catch (DataAccessException e) {
            return null;
        }
    }

}
