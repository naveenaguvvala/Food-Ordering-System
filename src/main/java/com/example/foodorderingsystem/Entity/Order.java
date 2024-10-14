package com.example.foodorderingsystem.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int orderId;

    @OneToMany(mappedBy = "itemCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items; //Can be improved with quantity

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    private User user;

    @Column(nullable = false)
    private String status;
}
