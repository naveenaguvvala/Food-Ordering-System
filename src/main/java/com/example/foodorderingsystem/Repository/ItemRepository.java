package com.example.foodorderingsystem.Repository;

import com.example.foodorderingsystem.DTOs.ItemDTO;
import com.example.foodorderingsystem.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT DISTINCT new com.example.foodorderingsystem.DTOs.ItemDTO(i1.itemName,i1.itemCode) FROM Item i1")
    List<ItemDTO> findDistinctByItemName();

    Optional<Item> findByRestaurant_RestaurantIdAndItemCode(Long restaurantId, String itemCode);
}
