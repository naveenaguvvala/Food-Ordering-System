package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Repository.ItemRepository;
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
    private ItemRepository itemRepository;

    // Define a ScheduledExecutorService with a fixed thread pool (can be adjusted)
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);


    @KafkaListener(topics = "item-preparation", groupId = "group_id")
    public void consume(Map<String, Object> message) {
        String itemCode = (String) message.get("itemId");
        int quantity = (int) message.get("quantity");
        long preparationTime = ((Number) message.get("preparationTime")).longValue();
        long restaurantId = ((Number) message.get("restaurantId")).longValue();

        scheduler.schedule(() -> processItemPreparation(itemCode, quantity, restaurantId),
                preparationTime, TimeUnit.MILLISECONDS);
    }

    private void processItemPreparation(String itemCode, int quantity, long restaurantId) {
        // Fetch the item from the repository
        Item item = itemRepository.findByRestaurant_RestaurantIdAndItemCode(restaurantId, itemCode)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        // Update the available quantity
        item.setAvailableQuantity(item.getAvailableQuantity() + quantity);

        // Save the updated item back to the database
        itemRepository.save(item);
    }
}

