package com.paul.mcgranaghan.webserver.api;

import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.repository.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserDao userDao;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @PostMapping("/users")
    void addUser(@RequestBody User user) {
        userDao.save(user);
    }

    public boolean existsById(String id) {
        return userDao.existsById(id);
    }
}