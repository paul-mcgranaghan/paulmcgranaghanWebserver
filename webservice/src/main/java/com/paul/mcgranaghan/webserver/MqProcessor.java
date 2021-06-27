package com.paul.mcgranaghan.webserver;

public class MqProcessor {

    public void processMessage(Object message) {
        System.out.println("Received message " + message);
    }
}
