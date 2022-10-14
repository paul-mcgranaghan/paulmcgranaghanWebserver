package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.NameBasics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.naming.Name;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class NameBasicDao implements CrudRepository<NameBasics, String> {

/*    private final static String GET_ACTOR_BY_NAME = """
                        SELECT "_id", nconst, "primaryName", "birthYear", "deathYear", "primaryProfession", "knownForTitles" 
                         FROM name_basics
                        WHERE "primaryName" = :primaryName
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
    }*/

    @Override
    public <S extends NameBasics> S save(S entity) {
        return null;
    }

    @Override
    public <S extends NameBasics> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<NameBasics> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<NameBasics> findAll() {
        return null;
    }

    @Override
    public Iterable<NameBasics> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(NameBasics entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends NameBasics> entities) {

    }

    @Override
    public void deleteAll() {

    }

    public NameBasics findByName(String name){
        return (NameBasics)
    }
}
