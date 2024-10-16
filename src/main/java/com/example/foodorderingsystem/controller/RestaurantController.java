package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.DTOs.ItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Restaurant;
import com.example.foodorderingsystem.Service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantController {


    @Autowired
    RestaurantService restaurantService;

    Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @PostMapping("/createRestaurant")
    public ResponseEntity<Object> register(@RequestBody Restaurant restaurant) {
        try{
            restaurantService.registerRestaurant(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        }
        catch(Exception e){
            logger.error("Restaurant creation failed - " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Restaurant Already Exists");
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO >> getItems() {
        List<ItemDTO> items = restaurantService.getAllItems();

        if(!items.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(items);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(items);
    }

    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        try {
                restaurantService.addItemToRestaurant(item);
                return ResponseEntity.status(HttpStatus.CREATED).body(item);
        }
        catch(Exception e){
            logger.error("Item addition failed - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/items")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        try {
            restaurantService.updateItem(item);
            return ResponseEntity.ok().body(item);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(item);
        }
    }

    @DeleteMapping("/items")
    public ResponseEntity<Item> deleteItem(@RequestBody Item item) {
        //Validation to check whether the item is there or not
        return ResponseEntity.ok().body(item);
    }
}
