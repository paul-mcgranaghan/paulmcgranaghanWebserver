package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
/*@RequiredArgsConstructor*/
public class UserRepository {

    private static final String GET_ALL_USERS_SQL = "SELECT * FROM \"User\"";
    private static final String INSERT_INTO_USER_SQL = "INSERT INTO \"User\" (user_id, age, name ,email, last_updated) values (:user_id, :age, :name, :email, CURRENT_TIMESTAMP)";
    private static final String GET_NEXT_USER_SEQ_SQL = "SELECT nextval('public.User_ID_Seq')";
    private static final String GET_USER_BY_ID = GET_ALL_USERS_SQL + " WHERE user_id= :user_id";

    //@Autowired
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<User> userRowMapper = new UserRowMapper();

    /*    @Bean
        @Value("dataSource")
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }

        @Bean
        @Value("namedParameterJdbcTemplate")
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Autowired @Qualifier("dataSource") DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    */
    public UserRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }

    public void save(User entity) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", entity.getName());
        paramMap.put("email", entity.getEmail());
        paramMap.put("user_id", getNextUserId());
        paramMap.put("age", entity.getAge());

        namedParameterJdbcTemplate.update(INSERT_INTO_USER_SQL, paramMap);
    }

    public boolean existsById(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", id);

        User requestedUser = null;
        try {
            requestedUser = namedParameterJdbcTemplate.queryForObject(GET_USER_BY_ID, paramMap, User.class);
        } catch (DataAccessException e) {
            log.error("Cannot get user id={}", id, e);
        }
        return requestedUser == null;
    }

    private String getNextUserId() {
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
