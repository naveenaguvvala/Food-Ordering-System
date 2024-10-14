package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.Entity.User;
import com.example.foodorderingsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
