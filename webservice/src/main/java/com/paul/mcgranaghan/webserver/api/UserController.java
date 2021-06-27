package com.paul.mcgranaghan.webserver.api;

import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.repository.UserDao;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final static String QUEUE_NAME = "rabbit@f26b8953c06b";
    private final UserDao userDao;

    @GetMapping("/users/listAll")
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @PostMapping("/users/addUser")
    void addUser(@RequestBody User user) {
        userDao.save(user);
    }

    void receiveMessage() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setHost("localhost");
        Connection connection = factory.newConnection("http://192.168.1.86:15672/#/");
        Channel channel = connection.createChannel();
        channel.queueDeclare(1, QUEUE_NAME);

        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    }

    public boolean existsById(String id) {
        return userDao.existsById(id);
    }
}