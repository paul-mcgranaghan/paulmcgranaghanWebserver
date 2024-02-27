package com.paul.mcgranaghan.webserver.integrationTest.base;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class ContainerBase {

    public static final PostgreSQLContainer DB_CONTAINER;

    static  {
        int containerPort = 5432 ;
        int localPort = 5432 ;

        DB_CONTAINER = new PostgreSQLContainer<>("postgres:13.6-alpine")
                .withDatabaseName("test")
                .withUsername("root")
                .withPassword("root")
                .withReuse(true)
                .withExposedPorts(containerPort)
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
                ));

        DB_CONTAINER.start();
    }
    @Container
    private static PostgreSQLContainer container = DB_CONTAINER;

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", DB_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DB_CONTAINER::getPassword);
        registry.add("spring.datasource.url", DB_CONTAINER::getJdbcUrl);
    }
}
