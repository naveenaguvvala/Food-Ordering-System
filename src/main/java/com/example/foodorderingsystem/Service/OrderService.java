package com.example.foodorderingsystem.Service;


import com.example.foodorderingsystem.DTOs.ItemDTO;
import com.example.foodorderingsystem.DTOs.OrderDTO;
import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private Set<String> itemCodes;

    public List<Item> fetchItemsByItemCode(List<OrderDTO> order) {
        return orderRepository.findItemsByItemCodes(extractItemCodes(order));
    }

    private Set<String> extractItemCodes(List<OrderDTO> order) {
         itemCodes = order.stream()
                .map(OrderDTO::getItemDTO)
                .map(ItemDTO::getItemCode)
                .collect(Collectors.toSet());
        return itemCodes;
    }

    public boolean areAllItemCodesPresent(List<Item> DbItems) {
        Set<String> DBItemCodes = DbItems.stream().map(Item::getItemCode).collect(Collectors.toSet());
        return DBItemCodes.containsAll(itemCodes);
    }
}
