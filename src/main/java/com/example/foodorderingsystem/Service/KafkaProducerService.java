package com.example.foodorderingsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "item-preparation";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPreparationEvent(long restaurantId, int quantity, long preparationTime) {
        Map<String, Object> message = new HashMap<>();
        message.put("restaurantId", restaurantId);
        message.put("quantity", quantity);
        message.put("preparationTime", preparationTime);

        // Send the message to Kafka
        kafkaTemplate.send(TOPIC, message);
    }
}
