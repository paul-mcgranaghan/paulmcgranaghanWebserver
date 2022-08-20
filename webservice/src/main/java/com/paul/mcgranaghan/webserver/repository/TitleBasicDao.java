package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class TitleBasicDao {
    private final static String GET_TITLE_BY_ID = """
                        SELECT _id, titleType, primaryTitle, originalTitle, isAdult, startYear, endYear, runtimeMinutes, genres
                        FROM title_basics
                        WHERE _id = :_id
            """;
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<TitleBasics> findById(List<String> _id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_id", _id);

        try {
            return namedParameterJdbcTemplate.queryForList(GET_TITLE_BY_ID, paramMap, TitleBasics.class);
        } catch (DataAccessException e) {
            return null;
        }
    }

}
