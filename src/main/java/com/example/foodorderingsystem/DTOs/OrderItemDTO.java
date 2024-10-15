package com.example.foodorderingsystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemDTO {
    private String itemCode;

    private Long res_id;

    private int quantity;

    private String itemName;
}
