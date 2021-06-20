package com.paul.mcgranaghan.webserver.integration;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

@RunWith(ConcordionRunner.class)
public class SplittingNamesFixture {

    public Result split(String fullName, String test) {
        Result result = new Result();
        String[] words = fullName.split(" ");
        result.firstName = words[0];
        result.lastName = words[1];
        return result;
    }

    public String split(String name) {
        return null;
    }

    static class Result {
        public String firstName;
        public String lastName;
    }
}