package com.example.foodorderingsystem.DTOs;

import com.example.foodorderingsystem.constants.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int userId;

    private String userName;

    private int resId;

    private Roles role;
}
