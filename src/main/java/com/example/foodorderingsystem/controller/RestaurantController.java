package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.DTOs.RestaurantDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Restaurant;
import com.example.foodorderingsystem.Repository.RestaurantRepository;
import com.example.foodorderingsystem.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantController {


    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/createRestaurant")
    public ResponseEntity<String> register(@RequestBody RestaurantDTO restaurant) {
        try{
            restaurantService.registerRestaurant(restaurant);
            return ResponseEntity.ok("Restaurant registered successfully");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        //If item_id already exists, return
        //Else put it in DB and return 200
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PatchMapping("/items")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        // Add Validation - if Item doesn't exist, then return some exception
        // Else update the DB object and put back into Table
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping("/items")
    public ResponseEntity<Item> deleteItem(@RequestBody Item item) {
        //Validation to check whether the item is there or not
        return ResponseEntity.ok().body(item);
    }
}
