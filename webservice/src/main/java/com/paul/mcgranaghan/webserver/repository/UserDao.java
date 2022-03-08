package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDao {


    private final static Log log = LogFactory.getLog(UserDao.class);

    private static final String GET_ALL_USERS_SQL = "SELECT * FROM \"User\"";
    private static final String INSERT_INTO_USER_SQL = "INSERT INTO \"User\" (user_id, age, name ,email, last_updated, dark_mode) values (:user_id, :age, :name, :email, CURRENT_TIMESTAMP, true)";
    private static final String GET_NEXT_USER_SEQ_SQL = "SELECT nextval('public.User_ID_Seq')";
    private static final String GET_USER_BY_ID = GET_ALL_USERS_SQL + " WHERE user_id = :user_id";
    private static final String UPDATE_DARK_MODE_SQL = "UPDATE \"User\" set dark_mode = :dark_mode where user_id = :user_id ";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<User> userRowMapper = new UserRowMapper();


    public void save(User entity) {
        String userId = getNextUserId();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", entity.getName());
        paramMap.put("email", entity.getEmail());
        paramMap.put("user_id", userId);
        paramMap.put("age", entity.getAge());
        log.info("Inserting new User: " + userId);
        namedParameterJdbcTemplate.update(INSERT_INTO_USER_SQL, paramMap);
    }

    public void setDarkMode(boolean darkMode, String userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dark_mode", darkMode);
        paramMap.put("user_id", userId);

        log.info("Setting dark mode to: " + darkMode + " for user id: " + userId);
        namedParameterJdbcTemplate.update(UPDATE_DARK_MODE_SQL, paramMap);
    }

    public boolean existsById(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", id);

        try {
            User requestedUser = namedParameterJdbcTemplate.queryForObject(GET_USER_BY_ID, paramMap, User.class);
            return requestedUser != null;
        } catch (DataAccessException e) {
            log.error("Cannot get user id: " + id, e);
        }
        return false;
    }

    private String getNextUserId() {
        log.info("Getting next user id");
        Map<String, Object> paramMap = new HashMap<>();
        return "U" + namedParameterJdbcTemplate.queryForObject(GET_NEXT_USER_SEQ_SQL, paramMap, Integer.class);
    }

    public List<User> findAll() {
        log.info("Requesting all user info");
        try {
            return namedParameterJdbcTemplate.query(GET_ALL_USERS_SQL, userRowMapper);
        } catch (DataAccessException e) {
            log.error("Cannot access user info", e);
        }
        return null;
    }
}
