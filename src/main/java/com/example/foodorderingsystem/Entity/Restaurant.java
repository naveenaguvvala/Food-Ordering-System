package com.example.foodorderingsystem.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String restaurantId;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    private String address;

    @Column
    private int currentCapacity;

//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = false)
//    private Date createdAt;
//
//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = false)
//    private Date updatedAt;
}
