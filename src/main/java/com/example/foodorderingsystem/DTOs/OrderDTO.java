package com.example.foodorderingsystem.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private ItemDTO itemDTO;

    private int quantity;
}
