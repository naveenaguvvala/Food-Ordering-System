package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.DTOs.ItemDTO;
import com.example.foodorderingsystem.DTOs.OrderDTO;
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
import java.util.Set;

@RestController
public class OrderController {


        @Autowired
        OrderService orderService;

        @PostMapping("/placeOrder")
        public ResponseEntity<Object> placeOrder(@RequestBody List<OrderDTO> orderDTOList) {
                try {
                        List<Item> items = orderService.fetchItemsByItemCode(orderDTOList);
                        if(!orderService.areAllItemCodesPresent(items)) {
                                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Order can't be placed due to invalid Items");
                        }
                }
                catch (Exception e) {

                }
                 //Core Logic
                return ResponseEntity.ok("Success");
        }

        @GetMapping("/orders/{userId}")
        public ResponseEntity<List<Order>> getOrders() {
                List<Order> orders = new ArrayList<>();
                //Query the Order table to fetch the orders of particular user
                return ResponseEntity.ok(orders);
        }
}
