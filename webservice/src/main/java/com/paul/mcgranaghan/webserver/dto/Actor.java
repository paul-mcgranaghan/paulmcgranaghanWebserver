package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Document(indexName = "actor")
public class Actor {

    @Id
    public String name;

    @Field(type = FieldType.Nested, includeInParent = true)
    public List<String> rolesList;
}
