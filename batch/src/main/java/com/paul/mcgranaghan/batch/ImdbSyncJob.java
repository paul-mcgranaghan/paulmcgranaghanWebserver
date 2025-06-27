package com.paul.mcgranaghan.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
@Component
public class ImdbSyncJob {

    @Value("${data.file.location}")
    private String dataLocation;

    @Value("${imdb.dataset.source.URI}")
    private String imdbDatasetSourceUri;

    @Autowired
    private ApplicationContext appContext;

    public void run() {
        log.info("Starting Imdb files syncing");

        RestTemplate restTemplate = new RestTemplate();

        File nameBasicsFile = restTemplate.execute(imdbDatasetSourceUri + "name.basics.tsv.gz", HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File(dataLocation + "name.basics.tsv.gz");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });



        File titleBasicsFile = restTemplate.execute(imdbDatasetSourceUri + "title.basics.tsv.gz", HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File(dataLocation + "title.basics.tsv.gz");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });


        File titlePrincipalsFile = restTemplate.execute(imdbDatasetSourceUri + "title.principals.tsv.gz", HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File(dataLocation + "title.principals.tsv.gz");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });


        log.info("Done with Imdb files syncing");
        SpringApplication.exit(appContext, () -> 0);
    }
}
