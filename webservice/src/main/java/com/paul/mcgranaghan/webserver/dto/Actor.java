package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Actor {

    public String name;
    public List<String> rolesList;
}
