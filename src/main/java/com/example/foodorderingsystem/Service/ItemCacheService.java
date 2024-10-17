package com.example.foodorderingsystem.Service;

import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCacheService {

    @Autowired
    private ItemRepository itemRepository;

    // Cache items based on itemCode with an expiry time
    @Cacheable(value = "itemsCache", key = "#itemCode", unless = "#result == null")
    public List<Item> findItemsByItemCode(String itemCode) {
//        return itemRepository.findItemsByItemCodeOrderByPrice(itemCode);
        List<Item> items = itemRepository.findItemsByItemCodeOrderByPrice(itemCode);
        System.out.println("Items fetched from DB: " + items);
        return items;
    }

    // Method to evict or refresh cache after updating item quantity
    @CacheEvict(value = "itemsCache", key = "#item.itemCode")
    public void addOrUpdateItem(Item item) {
         itemRepository.save(item);
    }

//    @CacheEvict(value = "itemsCache", key = "#itemCode")
//    public void deleteItem(String itemCode) {
//        itemRepository.deleteByItemCode(itemCode);
//    }
}
