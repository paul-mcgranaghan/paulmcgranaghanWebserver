package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NameBasics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    public String nConst;
    public String primaryName;
    public int birthYear;
    public int deathYear;
    public Set<Profession> primaryProfession;
    public Set<String> knownForTitles;
}
