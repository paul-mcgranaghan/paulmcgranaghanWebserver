package com.paul.mcgranaghan.webserver.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.paul.mcgranaghan.webserver.dto.NameBasics;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


@Repository
@RequiredArgsConstructor
public class NameBasicDao  {
    @Autowired
    private final MongoClient mongoClient;

    public NameBasics findById(String id) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("imdb");
        MongoCollection<NameBasics> mongoCollection = mongoDatabase.getCollection("name.basics", NameBasics.class);

        return mongoCollection.find(eq("_id", id)).first();
    }

}
