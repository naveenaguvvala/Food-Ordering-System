package com.example.foodorderingsystem.Repository;

import com.example.foodorderingsystem.Entity.Item;
import com.example.foodorderingsystem.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT i FROM Item i WHERE i.itemCode IN :itemCodes")
    List<Item> findItemsByItemCodes(@Param("itemCodes") Set<String> itemCodes);
}
