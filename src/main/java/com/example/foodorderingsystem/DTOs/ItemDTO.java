package com.example.foodorderingsystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class ItemDTO {

    private String itemName;

    private String itemCode;

    public ItemDTO(String itemName, String itemCode) {
        this.itemName = itemName;
        this.itemCode = itemCode;
    }
}
