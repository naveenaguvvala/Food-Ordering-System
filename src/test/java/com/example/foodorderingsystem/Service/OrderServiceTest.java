package com.example.foodorderingsystem.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.foodorderingsystem.DTOs.ItemDTO;
import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.DTOs.OrderItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Strategy.LowestCostSelectionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderServiceTest {

    @Mock
    private ItemCacheService itemCacheService;

    @Mock
    private LowestCostSelectionStrategy restaurantSelectionStrategy;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessOrder_Success() {
        // Mock data
        String itemCode = "ITEM123";
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItemDTO(new ItemDTO("Chicken Majestic",itemCode));
        orderDTO.setQuantity(2);
        List<OrderDTO> orderDTOList = Collections.singletonList(orderDTO);

        List<Item> availableItems = new ArrayList<>();
        availableItems.add(Item.builder().itemCode(itemCode).itemName("Chicken Majestic").price(100.0).build());

        OrderItemDTO orderItemDTO = new OrderItemDTO(itemCode, 2L, 2, "Chicken Majestic");
        List<OrderItemDTO> placedOrderItems = Collections.singletonList(orderItemDTO);

        // Mocking behaviors
        when(itemCacheService.findItemsByItemCode(itemCode)).thenReturn(availableItems);
        when(restaurantSelectionStrategy.placeOrder(orderDTO, availableItems, itemCode)).thenReturn(placedOrderItems);

        // Call the method
        List<OrderItemDTO> result = orderService.processOrder(orderDTOList);

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderItemDTO, result.get(0));

        // Verify interactions
        verify(itemCacheService, times(1)).findItemsByItemCode(itemCode);
        verify(restaurantSelectionStrategy, times(1)).placeOrder(orderDTO, availableItems, itemCode);
    }

    @Test
    void testProcessOrder_ItemNotAvailable() {
        // Mock data
        String itemCode = "ITEM123";
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItemDTO(new ItemDTO("Chicken Majestic",itemCode));
        List<OrderDTO> orderDTOList = Collections.singletonList(orderDTO);

        // Mocking behaviors
        when(itemCacheService.findItemsByItemCode(itemCode)).thenReturn(Collections.emptyList());

        // Call and assert the exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.processOrder(orderDTOList);
        });

        assertEquals("Item with code ITEM123 is not available in any restaurant.", exception.getMessage());

        // Verify interactions
        verify(itemCacheService, times(1)).findItemsByItemCode(itemCode);
        verify(restaurantSelectionStrategy, never()).placeOrder(any(), any(), any());
    }

    @Test
    void testProcessOrder_MultipleOrders_Success() {
        // Mock data
        String itemCode1 = "ITEM123";
        String itemCode2 = "ITEM456";

        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setItemDTO(new ItemDTO( "Chicken Majestic",itemCode1));
        OrderDTO orderDTO2 = new OrderDTO();
        orderDTO2.setItemDTO(new ItemDTO("Chicken 65", itemCode2));
        List<OrderDTO> orderDTOList = List.of(orderDTO1, orderDTO2);

        List<Item> availableItems1 = new ArrayList<>();
        availableItems1.add(Item.builder().itemCode(itemCode1).itemName("Chicken Majestic").price(100).build());

        List<Item> availableItems2 = new ArrayList<>();
        availableItems2.add(Item.builder().itemCode(itemCode2).itemName("Chicken 65").price(200).build());

        OrderItemDTO orderItemDTO1 = new OrderItemDTO(itemCode1, 1L, 1, "Chicken Majestic");
        OrderItemDTO orderItemDTO2 = new OrderItemDTO(itemCode2, 2L, 1, "Chicken 65");

        // Mocking behaviors
        when(itemCacheService.findItemsByItemCode(itemCode1)).thenReturn(availableItems1);
        when(itemCacheService.findItemsByItemCode(itemCode2)).thenReturn(availableItems2);

        when(restaurantSelectionStrategy.placeOrder(orderDTO1, availableItems1, itemCode1))
                .thenReturn(Collections.singletonList(orderItemDTO1));
        when(restaurantSelectionStrategy.placeOrder(orderDTO2, availableItems2, itemCode2))
                .thenReturn(Collections.singletonList(orderItemDTO2));

        // Call the method
        List<OrderItemDTO> result = orderService.processOrder(orderDTOList);

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orderItemDTO1, result.get(0));
        assertEquals(orderItemDTO2, result.get(1));

        // Verify interactions
        verify(itemCacheService, times(1)).findItemsByItemCode(itemCode1);
        verify(itemCacheService, times(1)).findItemsByItemCode(itemCode2);
        verify(restaurantSelectionStrategy, times(1)).placeOrder(orderDTO1, availableItems1, itemCode1);
        verify(restaurantSelectionStrategy, times(1)).placeOrder(orderDTO2, availableItems2, itemCode2);
    }
}
