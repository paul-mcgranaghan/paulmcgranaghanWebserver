package com.paul.mcgranaghan.webserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepoConfig.class})
public class ApplicationConfig {

}
