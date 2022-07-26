package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NameBasics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BsonProperty(value = "_id")
    public String id;
    @BsonProperty(value = "nConst")
    public String nConst;
    @BsonProperty(value = "primaryName")
    public String primaryName;
    @BsonProperty(value = "birthYear")
    public String birthYear;
    @BsonProperty(value = "deathYear")
    public String deathYear;
    @BsonProperty(value = "primaryProfession")
    public List<Profession> primaryProfession;
    @BsonProperty(value = "knownForTitles")
    public List<String> knownForTitles;
}
