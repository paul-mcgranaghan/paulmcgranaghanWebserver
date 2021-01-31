package com.paul.mcgranaghan.webserver.unit;

import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserDaoTest {

    @Mock
    private final NamedParameterJdbcTemplate mockTemplate = Mockito.mock(NamedParameterJdbcTemplate.class, Mockito.RETURNS_DEEP_STUBS);

    private final UserDao underTest = new UserDao(mockTemplate);

    @Test
    public void saveUserEntity() {

        User user = User.builder()
                .email("paul@email.com")
                .name("Paul McGranaghan")
                .age(21)
                .build();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "Paul McGranaghan");
        paramMap.put("email", ("paul@email.com"));
        paramMap.put("age", 21);
        paramMap.put("user_id", "U1");

        when(mockTemplate.queryForObject(anyString(), anyMap(), eq(Integer.class))).thenReturn(Integer.valueOf("1"));
        underTest.save(user);

        verify(mockTemplate, times(1)).queryForObject(anyString(), anyMap(), eq(Integer.class));
        verify(mockTemplate, times(1)).update(Mockito.anyString(), eq(paramMap));

    }
}