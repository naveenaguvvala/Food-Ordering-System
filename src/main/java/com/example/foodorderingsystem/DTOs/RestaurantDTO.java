package com.example.foodorderingsystem.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {

    private String restaurantName;

    private Integer maxCapacity;

    private String address;

    private Integer currentCapacity = 0;
}
