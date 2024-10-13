package com.example.foodorderingsystem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {

    @Id
    private int itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int restaurantId;
}
