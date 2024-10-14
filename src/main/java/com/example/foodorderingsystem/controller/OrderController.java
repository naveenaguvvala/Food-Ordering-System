package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.DTOs.ItemDTO;
import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.DTOs.OrderItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Order;
import com.example.foodorderingsystem.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {


        @Autowired
        OrderService orderService;

        @PostMapping("/placeOrder")
        public ResponseEntity<Object> placeOrder(@RequestBody List<OrderDTO> orderDTOList) {
                try {
                        List<OrderItemDTO> orderItems = orderService.processOrder(orderDTOList);
                        return ResponseEntity.status(HttpStatus.CREATED).body(orderItems);
                }
                catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping("/orders/{userId}")
        public ResponseEntity<List<Order>> getOrders() {
                List<Order> orders = new ArrayList<>();
                //Query the Order table to fetch the orders of particular user
                return ResponseEntity.ok(orders);
        }
}
