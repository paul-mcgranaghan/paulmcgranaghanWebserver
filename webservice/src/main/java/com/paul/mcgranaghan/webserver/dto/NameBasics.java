package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class NameBasics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String _id;
    public String nConst;
    public String primaryName;
    public String birthYear;
    public String deathYear;
    public Set<Profession> primaryProfessions;
    public Set<String> knownTitles;

    @BsonCreator
    public NameBasics(@BsonProperty(value = "_id") String _id, @BsonProperty(value = "nconst") String nConst,
                      @BsonProperty(value = "primaryName") String primaryName, @BsonProperty(value = "birthYear") String birthYear,
                      @BsonProperty(value = "deathYear") String deathYear, @BsonProperty(value = "primaryProfession") String primaryProfession,
                      @BsonProperty(value = "knownForTitles") String knownForTitles) {
        this._id = _id;
        this.nConst = nConst;
        this.primaryName = primaryName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.primaryProfessions = Arrays.stream(primaryProfession.split(","))
                .map(p -> Profession.valueOf(p.toUpperCase())).collect(Collectors.toSet());
        this.knownTitles = Arrays.stream(knownForTitles.split(",")).collect(Collectors.toSet());
    }
}
