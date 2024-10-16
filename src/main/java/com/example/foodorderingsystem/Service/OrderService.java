package com.example.foodorderingsystem.Service;


import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.DTOs.OrderItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Repository.ItemRepository;
import com.example.foodorderingsystem.Strategy.RestaurantSelectionStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    ItemCacheService itemCacheService;

    @Autowired
    RestaurantSelectionStrategy restaurantSelectionStrategy;

    @Transactional
    public List<OrderItemDTO> processOrder(List<OrderDTO> orderDTOList) {
        List<OrderItemDTO> orderItems = new ArrayList<>();

        for (OrderDTO orderDTO : orderDTOList) {
            String itemCode = orderDTO.getItemDTO().getItemCode();

            // Fetch items for the given itemCode, ordered by price (ascending)
            List<Item> availableItems = itemCacheService.findItemsByItemCode(itemCode);

            if (availableItems.isEmpty()) {
                throw new IllegalArgumentException("Item with code " + itemCode + " is not available in any restaurant.");
            }
            orderItems.addAll(restaurantSelectionStrategy.placeOrder(orderDTO, availableItems, itemCode));
        }
        return orderItems; // Return the list of OrderItemDTOs after successfully placing the order
    }
}
