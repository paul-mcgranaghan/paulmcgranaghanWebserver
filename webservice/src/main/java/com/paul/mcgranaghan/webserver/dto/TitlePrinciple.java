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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name="title_principals")
@NoArgsConstructor
@Entity
public class TitlePrinciple {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String _id;
    public String tconst;
    public Integer ordering;
    public String nconst;
    public PrincipleCategory principleCategory;
    public String job;
    public String charactersPlayed;

/*    public TitlePrinciple(String _id, String tconst, Integer ordering, String nconst,
                          String category, String job,
                          String characters) {
        this._id = _id;
        this.tconst = tconst;
        this.ordering = ordering;
        this.nconst = nconst;
        this.principleCategory = PrincipleCategory.valueOf(category.toUpperCase());
        this.job = job;
        this.charactersPlayed = Arrays.stream(characters.split(",")).collect(Collectors.toList());

    }*/
}
