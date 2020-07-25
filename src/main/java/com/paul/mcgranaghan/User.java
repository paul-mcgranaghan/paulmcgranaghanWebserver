package com.paul.mcgranaghan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String email;

    
    public User(){
        this.name = "";
        this.email = "";
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

}