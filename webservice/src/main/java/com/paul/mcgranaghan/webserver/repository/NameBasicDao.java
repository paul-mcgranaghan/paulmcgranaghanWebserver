package com.paul.mcgranaghan.webserver.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.paul.mcgranaghan.webserver.dto.NameBasics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;


@Repository
@RequiredArgsConstructor
public class NameBasicDao {
    @Autowired
    private final MongoClient mongoClient;

    public NameBasics findByName(String name) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("imdb");
        MongoCollection<NameBasics> mongoCollection = mongoDatabase.getCollection("name.basics", NameBasics.class);

        return mongoCollection.find(eq("primaryName", name)).first();
    }

}
