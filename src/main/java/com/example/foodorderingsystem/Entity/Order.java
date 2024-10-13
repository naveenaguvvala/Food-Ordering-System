package com.example.foodorderingsystem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private int orderId;

//    private Map<Item, Integer> items;

    @Column(nullable = false)
    private int userId;

    private String status;
}
