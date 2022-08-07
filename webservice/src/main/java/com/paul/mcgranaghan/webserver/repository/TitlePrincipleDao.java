package com.paul.mcgranaghan.webserver.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;


@Repository
@RequiredArgsConstructor
public class TitlePrincipleDao {
    @Autowired
    private final MongoClient mongoClient;

    public FindIterable<TitlePrinciple> fineRolesForPerson(String id) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("imdb");
        MongoCollection<TitlePrinciple> mongoCollection = mongoDatabase.getCollection("title.principals", TitlePrinciple.class);

        return mongoCollection.find(eq("nconst", id));
    }

}
