package com.paul.mcgranaghan.webserver.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;


@Repository
@RequiredArgsConstructor
public class TitleBasicDao {
    @Autowired
    private final MongoClient mongoClient;

    public TitleBasics findById(String id) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("imdb");
        MongoCollection<TitleBasics> mongoCollection = mongoDatabase.getCollection("title.basics", TitleBasics.class);

        return mongoCollection.find(eq("_id", id)).first();
    }

    public FindIterable<TitleBasics> findByTitle(String title) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("imdb");
        MongoCollection<TitleBasics> mongoCollection = mongoDatabase.getCollection("title.basics", TitleBasics.class);

        return mongoCollection.find(eq("primaryTitle", title));
    }

}
