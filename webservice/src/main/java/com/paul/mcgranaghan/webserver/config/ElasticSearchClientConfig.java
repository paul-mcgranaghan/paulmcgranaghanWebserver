package com.paul.mcgranaghan.webserver.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;

import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClientConfig {


    // URL and API key
    @Value("${elasticSearch.URI}")
    String serverUrl;
    //String serverUrl="https://host.docker.internal:9200";

    @Value("${elasticSearch.API.key}")
    String apiKey;
    //String apiKey="cnRQQzc0MEIxbl9ESmdaVFZqbXg6TjNoREVIMDBSTDJvWXlGbDBwaWhCdw==";

    @Bean
    RestClient elasticSearchRestClient(){
        return RestClient
                .builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();
    }

    @Bean
    ElasticsearchTransport elasticsearchTransport(RestClient elasticSearchRestClient){
        return new RestClientTransport(
                elasticSearchRestClient, new JacksonJsonpMapper());
    }

    @Bean
    ElasticsearchClient esClient(ElasticsearchTransport elasticsearchTransport){
        return new ElasticsearchClient(elasticsearchTransport);
    }

}
