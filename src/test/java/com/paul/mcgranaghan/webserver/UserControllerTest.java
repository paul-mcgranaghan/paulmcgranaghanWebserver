package com.paul.mcgranaghan.webserver;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {

    NamedParameterJdbcTemplate mockNamedParameterJdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class, Mockito.RETURNS_DEEP_STUBS);

    UserRepositoryImpl mockUserRepository = new UserRepositoryImpl(mockNamedParameterJdbcTemplate);
    public UserController underTest = new UserController(mockUserRepository);

    User user = User.builder().name("name").email("email").build();

    @Test
    public void getAllUsers_positive() {

        //Given
        List<User> expected = Collections.singletonList
                (user);
        when(mockNamedParameterJdbcTemplate.query(Mockito.anyString(),  ArgumentMatchers.<RowMapper<User>>any())).thenReturn(expected);

        //When
        Iterable<User> actual = underTest.getUsers();

        //Then
        assertThat(actual, is(expected));
    }
}