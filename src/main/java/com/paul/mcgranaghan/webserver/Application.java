package com.paul.mcgranaghan.webserver;

import com.paul.mcgranaghan.webserver.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationConfig.class})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
