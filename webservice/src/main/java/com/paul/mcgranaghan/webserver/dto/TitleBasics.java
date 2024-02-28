package com.paul.mcgranaghan.webserver.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name = "title_basics")
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
    public String genres;

}
