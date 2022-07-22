package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.NameBasics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NameBasicDao {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public NameBasics findItemById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), NameBasics.class, "name.basics");
    }

}
