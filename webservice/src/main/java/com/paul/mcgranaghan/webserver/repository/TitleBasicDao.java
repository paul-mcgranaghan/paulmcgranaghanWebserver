package com.paul.mcgranaghan.webserver.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


@Repository
@RequiredArgsConstructor
public class TitleBasicDao {
    @Autowired
    private final MongoClient mongoClient;

    public List<TitleBasics> findAllByIds(List<String> ids) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("imdb");
        MongoCollection<TitleBasics> mongoCollection = mongoDatabase.getCollection("title.basics", TitleBasics.class);

        MongoCollection<Document> mongoCollectiona = mongoDatabase.getCollection("title.basics", Document.class);
        //Document document = mongoCollectiona.find(eq("_id", ids)).first();
        var item = mongoCollectiona.find(eq("_id", ids.get(2))).first();

        List<TitleBasics> titleBasics = new ArrayList<>();

        for (String id : ids) {
            titleBasics.add(mongoCollection.find(eq("_id", id)).first());
        }
        return titleBasics;
    }

}
