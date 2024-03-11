package com.paul.mcgranaghan.webserver.config;


import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "springDataWithES.persistence")
@ComponentScan(basePackages = { "springDataWithES.services" })
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticSearch.URI}")
    private String ES_HOST_AND_PORT = "localhost:9200";
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(ES_HOST_AND_PORT)
                .withBasicAuth("elastic", "changeme")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}