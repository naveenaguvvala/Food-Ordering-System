package com.example.foodorderingsystem.Repository;

import com.example.foodorderingsystem.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    boolean existsByRestaurantName(String restaurantName);
}