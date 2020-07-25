package com.paul.mcgranaghan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaulsUserService implements UserService {

    @Autowired
    UserRepositoryImpl userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
