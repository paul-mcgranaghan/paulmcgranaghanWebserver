package com.paul.mcgranaghan.webserver;

import com.paul.mcgranaghan.webserver.config.MongoConfig;
import com.paul.mcgranaghan.webserver.config.MqConfig;
import com.paul.mcgranaghan.webserver.config.SecurityConfig;
import com.paul.mcgranaghan.webserver.repository.NameBasicsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/*@EnableMongoRepositories*/
@SpringBootApplication
@Import({MqConfig.class, SecurityConfig.class, MongoConfig.class})
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
