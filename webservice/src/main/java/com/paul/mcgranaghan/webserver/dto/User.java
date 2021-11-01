package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    public String email;
    public int age;
    public String name;
    public boolean darkMode;

}