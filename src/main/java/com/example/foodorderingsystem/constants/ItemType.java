package com.example.foodorderingsystem.constants;

public enum ItemType {
    CK65(1),
    CM(2),
    SDWH(10),
    PASTA(25),
    SALAD(5);

    private final int preparationTime; // Preparation time in minutes

    // Enum constructor
    ItemType(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    // Getter method to retrieve preparation time
    public int getPreparationTime() {
        return preparationTime;
    }
}
