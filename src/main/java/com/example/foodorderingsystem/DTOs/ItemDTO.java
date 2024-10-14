package com.example.foodorderingsystem.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter

public class ItemDTO {

    private String itemName;

    private String itemCode;

    public ItemDTO(String itemName, String itemCode) {
        this.itemName = itemName;
        this.itemCode = itemCode;
    }
}
