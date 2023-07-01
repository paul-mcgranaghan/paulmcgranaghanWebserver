package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.NameBasics;
import com.paul.mcgranaghan.webserver.dto.Profession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class NameBasicDao {

    private final static String GET_ACTOR_BY_NAME = """
                        SELECT "_id", nconst, "primaryName", "birthYear", "deathYear", "primaryProfession", "knownForTitles"
                         FROM name_basics
                        WHERE "primaryName" in (:primaryName);
            """;
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NameBasics findByName(String primaryName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("primaryName", primaryName);

        RowMapper<NameBasics> nameBasicsRowSet = (rs, rowNum) -> NameBasics.builder()
                ._id(rs.getString("_id"))
                .birthYear(rs.getString("birthYear"))
                .deathYear(rs.getString("deathYear"))
                .knownForTitles(rs.getString("knownForTitles"))
                .nConst(rs.getString("nConst"))
                .primaryProfession(rs.getString("primaryProfession"))
                .primaryProfessions(commaDelimitedToSetUtil(rs.getString("primaryProfession")))
                .primaryName(rs.getString("primaryName"))
                .knownTitles(commaDelimitedToSetUtilString(rs.getString("knownForTitles")))
                .build();

        try {
            return namedParameterJdbcTemplate.query(GET_ACTOR_BY_NAME, paramMap, nameBasicsRowSet).get(0);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void save(NameBasics nameBasics){

    }

    //Todo: Combine these as generic and move to a util class
    public Set<Profession> commaDelimitedToSetUtil(String primaryProfession) {
        return Arrays.stream(primaryProfession.split(","))
                .map(p -> Profession.valueOf(p.toUpperCase())).collect(Collectors.toSet());
    }

    public Set<String> commaDelimitedToSetUtilString(String primaryProfession) {
        return Arrays.stream(primaryProfession.split(","))
                .collect(Collectors.toSet());
    }
}
