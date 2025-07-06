package com.paul.mcgranaghan.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class BatchApplication implements CommandLineRunner {

    private final ImdbSyncJob imdbSyncJob;

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        imdbSyncJob.run();
    }
}
