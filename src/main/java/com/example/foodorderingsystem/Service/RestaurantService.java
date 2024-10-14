package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.DTOs.ItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Restaurant;
import com.example.foodorderingsystem.Repository.ItemRepository;
import com.example.foodorderingsystem.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Restaurant registerRestaurant(Restaurant restaurant) {
        // Check if restaurant already exists by name
        if (restaurantRepository.existsByEmail(restaurant.getEmail())) {
            throw new IllegalArgumentException("Restaurant with name " + restaurant.getRestaurantName() + " already exists");
        }
        // Save the new restaurant entity
        return restaurantRepository.save(restaurant);
    }

    public void addItemToRestaurant(Item item) {
        if(!restaurantRepository.existsById(item.getRestaurant().getRestaurantId())) {
            throw new IllegalArgumentException("Restaurant with id " + item.getRestaurant().getRestaurantId() + " does not exist");
        }
        itemRepository.save(item);
    }

    public List<ItemDTO> getAllItems() {
        return itemRepository.findDistinctByItemName();
    }

    public void updateItem(Item newItemData) {
        Optional<Item> optionalItem = itemRepository.findByRestaurant_RestaurantIdAndItemCode(newItemData.getRestaurant().getRestaurantId(), newItemData.getItemCode());
        if(optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();

            // Update the fields of the existing item
            existingItem.setItemName(newItemData.getItemName());
            existingItem.setPrice(newItemData.getPrice());
            existingItem.setAvailableQuantity(newItemData.getAvailableQuantity());

            // Save the updated item
            itemRepository.save(existingItem);
        }
        else {
            throw new IllegalArgumentException("Item with id " + newItemData.getRestaurant().getRestaurantId() + " does not exist");
        }
    }
}
