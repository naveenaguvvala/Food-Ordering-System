package com.example.foodorderingsystem.Strategy;

import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.DTOs.OrderItemDTO;
import com.example.foodorderingsystem.Entity.Item;

import java.util.List;

public interface RestaurantSelectionStrategy {
    List<OrderItemDTO> placeOrder(OrderDTO orderDTO, List<Item> availableItems, String itemCode);
}
