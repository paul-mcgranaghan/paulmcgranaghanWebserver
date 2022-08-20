package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
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
public class TitlePrincipleDao {
    private final static String GET_PRINCIPLE_BY_ACTOR_ID = """
                        SELECT _id, tconst, ordering, nconst, category, job, characters
                        FROM title_principle
                        WHERE nconst = :nconst
            """;
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<TitlePrinciple> findById(String nconst) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("nconst", nconst);

        try {
            return namedParameterJdbcTemplate.queryForList(GET_PRINCIPLE_BY_ACTOR_ID, paramMap, TitlePrinciple.class);
        } catch (DataAccessException e) {
            return null;
        }
    }

}
