package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.DTOs.RestaurantDTO;
import com.example.foodorderingsystem.Entity.Restaurant;
import com.example.foodorderingsystem.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public void registerRestaurant(RestaurantDTO restaurantDTO) {
        // Check if restaurant already exists by name
        if (restaurantRepository.existsByRestaurantName(restaurantDTO.getRestaurantName())) {
            throw new IllegalArgumentException("Restaurant with name " + restaurantDTO.getRestaurantName() + " already exists");
        }

        // Create new Restaurant entity from DTO
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(restaurantDTO.getRestaurantName());
        restaurant.setMaxCapacity(restaurantDTO.getMaxCapacity());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setCurrentCapacity(restaurantDTO.getCurrentCapacity());

        // Save the new restaurant entity
        restaurantRepository.save(restaurant);
    }
}
