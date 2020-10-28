package com.paul.mcgranaghan.webserver;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepositoryImpl userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    public boolean existsById(String id) {
        return  userRepository.existsById(id);
    }
}