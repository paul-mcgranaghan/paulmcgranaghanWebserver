package com.paul.mcgranaghan.webserver.service;

import com.paul.mcgranaghan.webserver.dto.Actor;
import com.paul.mcgranaghan.webserver.dto.NameBasics;
import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
import com.paul.mcgranaghan.webserver.repository.NameBasicDao;
import com.paul.mcgranaghan.webserver.repository.TitleBasicsRepository;
import com.paul.mcgranaghan.webserver.repository.TitlePrincipleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActorRolesService {

    @Autowired
    public final NameBasicDao nameBasicDao;

    @Autowired
    public final TitleBasicsRepository titleBasicsRepository;

    @Autowired
    public final TitlePrincipleRepository titlePrincipleDao;

    public Set<String> resolveRolesForActor(String actorName, String actorName2) {
        log.info("Requesting for : {} and {} ", actorName, actorName2);
        NameBasics nameBasics = nameBasicDao.findByName(actorName);
        List<TitlePrinciple> titlePrinciples = titlePrincipleDao.findByNconst(nameBasics.getNConst());
        List<TitleBasics> titleBasics = titleBasicsRepository.findBy_idIn(titlePrinciples.stream().map(TitlePrinciple::getTconst).collect(Collectors.toList()));
        Actor actor = Actor.builder().name(nameBasics.primaryName).rolesList(titleBasics.stream().map(t -> t.primaryTitle).toList()).build();

        NameBasics nameBasics2 = nameBasicDao.findByName(actorName2);
        List<TitlePrinciple> titlePrinciples2 = titlePrincipleDao.findByNconst(nameBasics2.getNConst());
        List<TitleBasics> titleBasics2 = titleBasicsRepository.findBy_idIn(titlePrinciples2.stream().map(TitlePrinciple::getTconst).collect(Collectors.toList()));
        // TODO: handle null responses, throw exception if dao's return null
        Actor actor2 = Actor.builder().name(nameBasics2.primaryName).rolesList(titleBasics2.stream().map(t -> t.primaryTitle).toList()).build();

        Set<String> commonRoles = actor.rolesList.stream().distinct().filter(actor2.rolesList::contains).collect(Collectors.toSet());

        log.info("For Actors {} and {}, returning roles: {}", actorName, actorName2,commonRoles);
        return commonRoles;


    }
}
