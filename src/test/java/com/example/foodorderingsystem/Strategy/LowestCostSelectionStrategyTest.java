package com.example.foodorderingsystem.Strategy;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.DTOs.OrderItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Restaurant;
import com.example.foodorderingsystem.Repository.RestaurantRepository;
import com.example.foodorderingsystem.Service.KafkaProducerService;
import com.example.foodorderingsystem.constants.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class LowestCostSelectionStrategyTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private LowestCostSelectionStrategy restaurantSelectionStrategy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder_SufficientStock_SingleRestaurant() {
        // Mock data
        String itemCode = "CM";
        Item item = Item.builder().
                itemCode(itemCode).
                itemName("Chicken Majestic").
                price(200.0).build();
        Restaurant restaurant = Restaurant.builder().
                                restaurantId(1L).
                                restaurantName("Meghana").
                                maxCapacity(10).build(); // MaxCapacity is 10
        item.setRestaurant(restaurant);

        List<Item> availableItems = List.of(item);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setQuantity(5); // Order 5 items

        // Call the method
        List<OrderItemDTO> result = restaurantSelectionStrategy.placeOrder(orderDTO, availableItems, "CM");

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getQuantity()); // Fulfilled from one restaurant
        assertEquals(1L, result.get(0).getRes_id());
        assertEquals("CM", result.get(0).getItemCode());

        // Verify repository save and Kafka producer
        verify(restaurantRepository, times(1)).save(restaurant);
        verify(kafkaProducerService, times(1)).sendPreparationEvent(1L, 5, ItemType.CM.getPreparationTime() * 60 * 1000);
    }

    @Test
    void testPlaceOrder_SufficientStock_MultipleRestaurants() {
        // Mock data
        String itemCode = "CM";
        Item item1 = Item.builder().
                itemCode(itemCode).
                itemName("Chicken Majestic").
                price(200.0).build();
        Restaurant restaurant1 = Restaurant.builder().
                restaurantId(1L).
                restaurantName("Restaurant A").
                maxCapacity(3).build(); // MaxCapacity is 3
        item1.setRestaurant(restaurant1);

        Item item2 = Item.builder().
                itemCode(itemCode).
                itemName("Chicken Majestic").
                price(200.0).build();
        Restaurant restaurant2 = Restaurant.builder().
                restaurantId(2L).
                restaurantName("Restaurant B").
                maxCapacity(5).build(); // MaxCapacity is 5
        item2.setRestaurant(restaurant2);

        List<Item> availableItems = List.of(item1, item2);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setQuantity(7); // Order 7 items

        // Call the method
        List<OrderItemDTO> result = restaurantSelectionStrategy.placeOrder(orderDTO, availableItems, itemCode);

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.size()); // Split between two restaurants
        assertEquals(3, result.get(0).getQuantity()); // 3 from Restaurant A
        assertEquals(1L, result.get(0).getRes_id());
        assertEquals(4, result.get(1).getQuantity()); // Remaining 4 from Restaurant B
        assertEquals(2L, result.get(1).getRes_id());

        // Verify repository save and Kafka producer
        verify(restaurantRepository, times(1)).save(restaurant1);
        verify(restaurantRepository, times(1)).save(restaurant2);
        verify(kafkaProducerService, times(1)).sendPreparationEvent(1L, 3, ItemType.CM.getPreparationTime() * 60 * 1000);
        verify(kafkaProducerService, times(1)).sendPreparationEvent(2L, 4, ItemType.CM.getPreparationTime() * 60 * 1000);
    }
//
    @Test
    void testPlaceOrder_InsufficientStock() {
        // Mock data
        String itemCode = "CM";
        Item item1 =Item.builder().
                itemCode(itemCode).
                itemName("Chicken Majestic").
                price(200.0).build();
        Restaurant restaurant1 = Restaurant.builder().
                restaurantId(1L).
                restaurantName("Restaurant A").
                maxCapacity(2).build(); // MaxCapacity is 2
        item1.setRestaurant(restaurant1);

        Item item2 = Item.builder().
                itemCode(itemCode).
                itemName("Chicken Majestic").
                price(200.0).build();
        Restaurant restaurant2 = Restaurant.builder().
                restaurantId(2L).
                restaurantName("Restaurant B").
                maxCapacity(3).build(); // MaxCapacity is 3
        item2.setRestaurant(restaurant2);

        List<Item> availableItems = List.of(item1, item2);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setQuantity(10); // Order 10 items

        // Assert exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            restaurantSelectionStrategy.placeOrder(orderDTO, availableItems, itemCode);
        });

        assertEquals("Not enough stock available for item with code CM", exception.getMessage());

        // Verify no saves or Kafka messages sent
//        verify(restaurantRepository, never()).save(any(Restaurant.class));
//        verify(kafkaProducerService, never()).sendPreparationEvent(anyLong(), anyInt(), anyLong());
    }

}
