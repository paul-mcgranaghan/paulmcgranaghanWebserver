package com.paul.mcgranaghan.webserver;

import com.paul.mcgranaghan.webserver.config.ApplicationConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@SpringBootApplication
@Import({ApplicationConfig.class})
public class Application {

    public static void main(String[] args) throws IOException {

        MqProcessor mqProcessor = new MqProcessor();

        ConnectionFactory factory = new ConnectionFactory();
        //factory.setHost("localhost");
        Connection connection = factory.newConnection("192.168.1.86", 5672);
        Channel channel = connection.createChannel();
        channel.queueDeclare(1, "rabbit@f26b8953c06b");

        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(1, "rabbit@f26b8953c06b", true, deliverCallback, consumerTag -> mqProcessor.processMessage(this.consumerTag));

        channel.basicConsume(1, "rabbit@f26b8953c06b", true, consumerTag -> {
        });

        SpringApplication.run(Application.class, args);
    }

}
