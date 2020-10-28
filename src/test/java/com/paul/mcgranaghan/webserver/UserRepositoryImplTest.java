package com.paul.mcgranaghan.webserver;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserRepositoryImplTest {

    @Mock
    private NamedParameterJdbcTemplate mockTemplate;

    private UserRepositoryImpl underTest = new UserRepositoryImpl(mockTemplate);

    @Test
    public void saveUserEntity() {

        User user = User.builder()
                .email("paul@email.com")
                .name("Paul McGranaghan")
                .build();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "Paul McGranaghan");
        paramMap.put("email", ("paul@email.com"));
        paramMap.put("user_id", "U1");

        when(mockTemplate.queryForObject(anyString(), anyMap(), Integer.class)).thenReturn(Integer.valueOf("1"));
        underTest.save(user);

        verify(mockTemplate.queryForObject(anyString(), anyMap(), Integer.class), times(1));
        verify(mockTemplate.update(anyString(), paramMap), times(1));

    }
}