package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name="name_basics")
@NoArgsConstructor
@Entity
public class NameBasics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String _id;
    public String nConst;
    public String primaryName;
    public String birthYear;
    public String deathYear;
    public String primaryProfession;
    public String knownForTitles;

/*    public NameBasics(String _id, String nConst,
                      String primaryName, String birthYear,
                      String deathYear, String primaryProfession,
                      String knownForTitles) {
        this._id = _id;
        this.nConst = nConst;
        this.primaryName = primaryName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.primaryProfession = primaryProfession;
        this.knownForTitles = knownForTitles;

        *//*this.primaryProfessions = Arrays.stream(primaryProfession.split(","))
                .map(p -> Profession.valueOf(p.toUpperCase())).collect(Collectors.toSet());
        this.knownTitles = Arrays.stream(knownForTitles.split(",")).collect(Collectors.toSet());*//*
    }*/
}
