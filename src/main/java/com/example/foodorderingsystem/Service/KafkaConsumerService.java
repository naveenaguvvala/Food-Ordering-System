package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Restaurant;
import com.example.foodorderingsystem.Repository.ItemRepository;
import com.example.foodorderingsystem.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaConsumerService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Define a ScheduledExecutorService with a fixed thread pool (can be adjusted)
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);


    @KafkaListener(topics = "item-preparation", groupId = "group_id")
    public void consume(Map<String, Object> message) {
        int quantity = (int) message.get("quantity");
        long preparationTime = ((Number) message.get("preparationTime")).longValue();
        long restaurantId = ((Number) message.get("restaurantId")).longValue();

        scheduler.schedule(() -> processItemPreparation(quantity, restaurantId),
                preparationTime, TimeUnit.MILLISECONDS);
    }

    private void processItemPreparation(int quantity, long restaurantId) {
        // Fetch the Restaurant from the repository
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Update the available quantity
        restaurant.setMaxCapacity(restaurant.getMaxCapacity() + quantity);

        System.out.println("Request Received");

        // Save the updated restaurant back to the database
        restaurantRepository.save(restaurant);
    }
}

