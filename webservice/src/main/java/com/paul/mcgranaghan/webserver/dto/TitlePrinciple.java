package com.paul.mcgranaghan.webserver.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    public PrincipleCategory category;
    public String job;
    public String characters;

}
