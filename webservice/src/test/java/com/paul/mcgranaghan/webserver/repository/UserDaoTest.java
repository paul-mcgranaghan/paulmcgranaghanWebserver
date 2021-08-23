package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    @Mock
    private final NamedParameterJdbcTemplate mockTemplate = Mockito.mock(NamedParameterJdbcTemplate.class, Mockito.RETURNS_MOCKS);

    private UserDao underTest;

    @Before
    public void before() {
        underTest = new UserDao(mockTemplate);
    }

    @Test
    public void saveUserEntity() {

        User user = User.builder()
                .email("paul@email.com")
                .name("Paul McGranaghan")
                .age(21)
                .build();

        underTest.save(user);

        verify(mockTemplate, times(1)).update(anyString(), anyMap());

    }

    @Test
    public void existsById() {
        when(mockTemplate.queryForObject(anyString(), anyMap(), Mockito.eq(User.class))).thenReturn(User.builder().id("U1").build());

        boolean actual = underTest.existsById("U2");
        assertTrue(actual);
    }
}