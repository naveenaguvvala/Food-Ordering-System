package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.DTOs.UserDTO;
import com.example.foodorderingsystem.Entity.User;
import com.example.foodorderingsystem.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(RestaurantController.class);
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDTO user) {
        try{
            User newUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User Login Failed");
    }
}
