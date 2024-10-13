package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.Entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<String> register(@RequestBody User user) {
        //Store the user details if the user is new
        return ResponseEntity.ok("success");
    }
}
