package com.example.foodorderingsystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "item", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"itemCode", "restaurant_id"})
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String itemCode; //It is unique for the items in the Restaurant, it can be used to group the Items

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name="restaurant_id", referencedColumnName = "restaurantId")
    private Restaurant restaurant;

    @Column(nullable = false)
    private int availableQuantity;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;
}
