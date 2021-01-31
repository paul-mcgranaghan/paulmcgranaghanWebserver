package com.paul.mcgranaghan.webserver.unit;

import com.paul.mcgranaghan.webserver.api.UserController;
import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
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

    private final NamedParameterJdbcTemplate mockNamedParameterJdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class, Mockito.RETURNS_DEEP_STUBS);
    private final User user = User.builder().name("name").email("email").age(12).build();

    @Mock
    private final UserDao mockUserDao = new UserDao(mockNamedParameterJdbcTemplate);
    private final UserController underTest = new UserController(mockUserDao);

    @Test
    public void getAllUsers_positive() {

        //Given
        List<User> expected = Collections.singletonList
                (user);
        when(mockNamedParameterJdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<RowMapper<User>>any())).thenReturn(expected);

        //When
        Iterable<User> actual = underTest.getUsers();

        //Then
        assertThat(actual, is(expected));
    }
}