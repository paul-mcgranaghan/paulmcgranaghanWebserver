package com.paul.mcgranaghan.webserver.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RepoConfig {

    /*@Bean
    @Value("dataSource")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    @Value("namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Autowired @Qualifier("dataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Value("userRepository")
    public UserRepository userRepository(@Autowired @Qualifier("namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new UserRepository(namedParameterJdbcTemplate);
    }*/

}
