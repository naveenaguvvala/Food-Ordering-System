package com.example.foodorderingsystem.Strategy;

import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.DTOs.OrderItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class LowestCostSelectionStrategy implements RestaurantSelectionStrategy{

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<OrderItemDTO> placeOrder(OrderDTO orderDTO, List<Item> availableItems, String itemCode) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        int remainingQuantity = orderDTO.getQuantity();

        for (Item item : availableItems) {
            if (remainingQuantity <= 0) {
                break; // The required quantity has been fulfilled
            }

            int availableQuantity = item.getAvailableQuantity();
            Long restaurantId = item.getRestaurant().getRestaurantId();

            if (availableQuantity >= remainingQuantity) {
                // Fulfill the remaining quantity from this restaurant
                item.setAvailableQuantity(availableQuantity - remainingQuantity);
                itemRepository.save(item);  // Update the item in the database

                // Add to the final order
                orderItems.add(new OrderItemDTO(itemCode, restaurantId, remainingQuantity, 300));
                remainingQuantity = 0;
            } else {
                // Take all available stock from this restaurant and move to the next
                orderItems.add(new OrderItemDTO(itemCode, restaurantId, availableQuantity, 300));
                remainingQuantity -= availableQuantity;
                item.setAvailableQuantity(0);  // All stock is taken
                itemRepository.save(item);  // Update the item in the database
            }
        }

        if (remainingQuantity > 0) {
            throw new IllegalArgumentException("Not enough stock available for item with code " + itemCode);
        }
        return orderItems;
    }
}