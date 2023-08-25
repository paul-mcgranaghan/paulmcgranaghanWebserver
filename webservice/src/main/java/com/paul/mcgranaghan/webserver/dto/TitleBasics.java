package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name="title_basics")
@NoArgsConstructor
@Entity
public class TitleBasics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String _id;
    public String titleType;
    public String primaryTitle;
    public String originalTitle;
    public boolean isAdult;
    public String startYear;
    public String endYear;
    public String runtimeMinutes;
    public String titleGenre;

}
