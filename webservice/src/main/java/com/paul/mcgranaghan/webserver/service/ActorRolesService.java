package com.paul.mcgranaghan.webserver.service;

import com.paul.mcgranaghan.webserver.dto.Actor;
import com.paul.mcgranaghan.webserver.dto.NameBasics;
import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
import com.paul.mcgranaghan.webserver.repository.NameBasicDao;
import com.paul.mcgranaghan.webserver.repository.TitleBasicDao;
import com.paul.mcgranaghan.webserver.repository.TitlePrincipleDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActorRolesService {

    @Autowired
    public final NameBasicDao nameBasicDao;

    @Autowired
    public final TitleBasicDao titleBasicDao;

    @Autowired
    public final TitlePrincipleDao titlePrincipleDao;

    public Actor resolveRolesForActor(String actorName) {
        NameBasics nameBasics = nameBasicDao.findByName(actorName);
        List<TitlePrinciple> titlePrinciples = titlePrincipleDao.findById(nameBasics.getNConst());
        List<TitleBasics> titleBasics = titleBasicDao.findById(titlePrinciples.stream().map(TitlePrinciple::getTconst).collect(Collectors.toList()));
        return Actor.builder().name(nameBasics.primaryName).rolesList(titleBasics.stream().map(t -> t.primaryTitle).toList()).build();

    }
}
