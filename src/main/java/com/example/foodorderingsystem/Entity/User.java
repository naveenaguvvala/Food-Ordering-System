package com.example.foodorderingsystem.Entity;

import com.example.foodorderingsystem.constants.Roles;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private int userId;

    private String userName;

    private int resId;

    private Roles role;
}
