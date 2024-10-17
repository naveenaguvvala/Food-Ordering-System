package com.example.foodorderingsystem.Strategy;

import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.DTOs.OrderItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Order;
import com.example.foodorderingsystem.Entity.Restaurant;
import com.example.foodorderingsystem.Repository.ItemRepository;
import com.example.foodorderingsystem.Repository.RestaurantRepository;
import com.example.foodorderingsystem.Service.ItemCacheService;
import com.example.foodorderingsystem.Service.KafkaProducerService;
import com.example.foodorderingsystem.constants.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class LowestCostSelectionStrategy implements RestaurantSelectionStrategy{

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public List<OrderItemDTO> placeOrder(OrderDTO orderDTO, List<Item> availableItems, String itemCode) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        int remainingQuantity = orderDTO.getQuantity();

        for (Item item : availableItems) {
            if (remainingQuantity <= 0) {
                break; // The required quantity has been fulfilled
            }

            Restaurant restaurant = item.getRestaurant();
            Long restaurantId = restaurant.getRestaurantId();
            restaurant = restaurantRepository.findById(restaurantId).get();
            int availableQuantity = restaurant.getMaxCapacity();

            if (availableQuantity >= remainingQuantity) {
                // Fulfill the remaining quantity from this restaurant
                restaurant.setMaxCapacity(availableQuantity - remainingQuantity);
                restaurantRepository.save(restaurant); // Update the Restaurant in the database

                // Add to the final order
                orderItems.add(new OrderItemDTO(itemCode, restaurantId, remainingQuantity, item.getItemName()));
                remainingQuantity = 0;
            } else {
                // Take all available stock from this restaurant and move to the next
                orderItems.add(new OrderItemDTO(itemCode, restaurantId, availableQuantity, item.getItemName()));
                remainingQuantity -= availableQuantity;
                restaurant.setMaxCapacity(0);  // All stock is taken
                restaurantRepository.save(restaurant);  // Update the item in the database
            }
        }

        if (remainingQuantity > 0) {
            throw new IllegalArgumentException("Not enough processing capacity available for item with code " + itemCode);
        }
        sendPreparationEvent(orderItems);
        return orderItems;
    }

    private void sendPreparationEvent(List<OrderItemDTO> orderItemDTOS) {
        for(OrderItemDTO orderItemDTO : orderItemDTOS) {
            kafkaProducerService.sendPreparationEvent(orderItemDTO.getRes_id(), orderItemDTO.getQuantity(), (long)ItemType.valueOf(orderItemDTO.getItemCode().toUpperCase()).getPreparationTime()*60*1000);
        }
    }
}
