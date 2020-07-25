package com.paul.mcgranaghan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<User> userRowMapper = new UserRowMapper();

    private static final String GET_ALL_USERS_SQL ="Select * From \"User\"";

    private static final String INSERT_INTO_USER = "INSERT INTO \"User\" (user_id, name ,email, last_updated) values (:user_id, :name, :email, CURRENT_TIMESTAMP)";

    Map<String, Object> paramMap = new HashMap<String, Object>();


    @Override
    public <S extends User> S save(S entity) {
        paramMap.put("name", entity.getName());
        paramMap.put("email", entity.getEmail());
        paramMap.put("user_id", "U1000");

        namedParameterJdbcTemplate.update(INSERT_INTO_USER, paramMap);
        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    public Iterable<User> findAll() {
        log.info("Requesting all user info");
        return namedParameterJdbcTemplate.query(GET_ALL_USERS_SQL, userRowMapper);
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}