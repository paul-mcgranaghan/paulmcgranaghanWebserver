package com.paul.mcgranaghan.webserver.integrationTest.base;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@CucumberContextConfiguration
@SpringBootTest
@CucumberOptions(plugin = {"pretty","html:./build/cucumber-reports",
        "junit:target/cucumber-reports/Cucumber.xml"}, features = {"src/test/resources"})
public class CucumberBase extends ContainerBase {


}