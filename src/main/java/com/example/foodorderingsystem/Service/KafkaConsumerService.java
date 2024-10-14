package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@Service
public class KafkaConsumerService {

    @Autowired
    private ItemRepository itemRepository;

    @KafkaListener(topics = "item-preparation", groupId = "group_id")
    public void consume(Map<String, Object> message) {
        String itemCode = (String) message.get("itemId");
        int quantity = (int) message.get("quantity");
        long preparationTime = ((Number) message.get("preparationTime")).longValue();
        long restaurantId = ((Number) message.get("restaurantId")).longValue();

        // Simulate the preparation time delay (e.g., wait for the specified time)
        try {
            Thread.sleep(preparationTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // After preparation time, release the quantity
        Item item = itemRepository.findByRestaurant_RestaurantIdAndItemCode(restaurantId, itemCode)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        item.setAvailableQuantity(item.getAvailableQuantity() + quantity);
        itemRepository.save(item);  // Update the item's available quantity
    }
}

