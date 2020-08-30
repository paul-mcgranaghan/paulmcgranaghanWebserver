package com.paul.mcgranaghan.webserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    private String name;
    public String email;

    
    public User(){
        this.name = "";
        this.email = "";
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

}