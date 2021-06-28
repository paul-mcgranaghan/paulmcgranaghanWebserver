package com.paul.mcgranaghan.webserver;

import com.paul.mcgranaghan.webserver.config.MqConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MqConfig.class})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
