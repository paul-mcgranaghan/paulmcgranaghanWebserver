package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ActorNameRepository extends ElasticsearchRepository<Actor, String> {

    Page<Actor> findByName(String name, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"Actor.name\": \"?0\"}}]}}")
    Page<Actor> findByActorsNameUsingCustomQuery(String name, Pageable pageable);


}


