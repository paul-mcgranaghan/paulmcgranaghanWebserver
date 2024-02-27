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
                        SELECT "_id", nconst, "primary_name", "birth_year", "death_year", "primary_profession", "known_for_titles"
                         FROM name_basics
                        WHERE "primary_name" in (:primary_name);
            """;
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NameBasics findByName(String primaryName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("primary_name", primaryName);

        RowMapper<NameBasics> nameBasicsRowSet = (rs, rowNum) -> NameBasics.builder()
                ._id(rs.getString("_id"))
                .birthYear(rs.getString("birth_year"))
                .deathYear(rs.getString("death_year"))
                .knownForTitles(rs.getString("known_for_titles"))
                .nConst(rs.getString("nconst"))
                .primaryProfession(rs.getString("primary_profession"))
                .primaryProfessions(commaDelimitedToSetUtil(rs.getString("primary_profession")))
                .primaryName(rs.getString("primary_name"))
                .knownTitles(commaDelimitedToSetUtilString(rs.getString("known_for_titles")))
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
