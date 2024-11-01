package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.DTOs.UserDTO;
import com.example.foodorderingsystem.Entity.User;
import com.example.foodorderingsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User registerUser(UserDTO user) {
        User newUser = User.builder().userName(user.getUserName()).password(passwordEncoder.encode(user.getPassword())).build();
        return userRepository.save(newUser);
    }
}
