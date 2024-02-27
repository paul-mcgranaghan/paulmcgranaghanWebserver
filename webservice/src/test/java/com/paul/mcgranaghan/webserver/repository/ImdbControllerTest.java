package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.api.ImdbController;
import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.service.ActorRolesService;
import com.paul.mcgranaghan.webserver.service.FlagsmithService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@RunWith(MockitoJUnitRunner.class)
public class ImdbControllerTest {

    private final NamedParameterJdbcTemplate mockNamedParameterJdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class, Mockito.RETURNS_DEEP_STUBS);

    private final User user = User.builder().name("name").email("email").age(12).build();


    @Mock
    private final NameBasicDao nameBasicDao = new NameBasicDao(mockNamedParameterJdbcTemplate);
    //@Mock
    //private final TitleBasicDao titleBasicDao = new TitleBasicDao(mockNamedParameterJdbcTemplate);
    //@Mock
    //private final TitlePrincipleDao titlePrincipleDao = new TitlePrincipleDao(mockNamedParameterJdbcTemplate);
    @Mock
    private final ActorRolesService actorRolesService = new ActorRolesService(nameBasicDao, null, null);

    @Mock
    private final FlagsmithService flagsmithService = new FlagsmithService();

    private final ImdbController underTest = new ImdbController(actorRolesService,flagsmithService);

    public ImdbControllerTest()  {
    }

    @Test
    public void getAllUsers_positive() {

    }
}