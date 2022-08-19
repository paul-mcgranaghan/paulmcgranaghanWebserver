package com.paul.mcgranaghan.webserver.repository;

import com.mongodb.client.MongoClient;
import com.paul.mcgranaghan.webserver.api.UserController;
import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.service.ActorRolesService;
import org.junit.Test;
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
public class UserControllerTest {

    private final NamedParameterJdbcTemplate mockNamedParameterJdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class, Mockito.RETURNS_DEEP_STUBS);
    private final MongoClient mongoClient = Mockito.mock(MongoClient.class, Mockito.RETURNS_DEEP_STUBS);
    private final User user = User.builder().name("name").email("email").age(12).build();

    @Mock
    private final UserDao mockUserDao = new UserDao(mockNamedParameterJdbcTemplate);

    @Mock
    private final NameBasicDao nameBasicDao = new NameBasicDao(mongoClient);
    @Mock
    private final TitleBasicDao titleBasicDao = new TitleBasicDao(mongoClient);
    @Mock
    private final TitlePrincipleDao titlePrincipleDao = new TitlePrincipleDao(mongoClient);
    @Mock
    private final ActorRolesService actorRolesService = new ActorRolesService(nameBasicDao, titleBasicDao, titlePrincipleDao);

    private final UserController underTest = new UserController(mockUserDao, actorRolesService);

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