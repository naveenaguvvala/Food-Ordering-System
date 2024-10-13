package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {


        @PostMapping("/order")
        public ResponseEntity<String> placeOrder(@RequestBody Order order) {
                return ResponseEntity.ok("Success");
        }

        @GetMapping("/orders/{userId}")
        public ResponseEntity<List<Order>> getOrders() {
                List<Order> orders = new ArrayList<>();
                //Query the Order table to fetch the orders of particular user
                return ResponseEntity.ok(orders);
        }

        @GetMapping("/items")
        public ResponseEntity<List<Item>> getItems() {
                List<Item> items = new ArrayList<>();
                //Query the Item table using Distinct
                return ResponseEntity.ok().body(items);
        }

}
