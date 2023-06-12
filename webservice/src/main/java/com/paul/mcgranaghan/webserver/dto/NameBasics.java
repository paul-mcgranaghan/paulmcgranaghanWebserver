package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
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
    public Set<Profession> primaryProfessions;
    public Set<String> knownTitles;

    public NameBasics(String _id, String nConst,
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

        this.primaryProfessions = Arrays.stream(primaryProfession.split(","))
                .map(p -> Profession.valueOf(p.toUpperCase())).collect(Collectors.toSet());
        this.knownTitles = Arrays.stream(knownForTitles.split(",")).collect(Collectors.toSet());
    }

    public void setPrimaryProfessionsFromString(String professions) {
        this.primaryProfessions = Arrays.stream(primaryProfession.split(","))
                .map(p -> Profession.valueOf(p.toUpperCase())).collect(Collectors.toSet());

    }
}
