package com.hillel.online_shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    private String email;

    private String login;

    private String password;

    private BigDecimal balance;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> order;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    public enum Role {
        ROLE_ADMIN, ROLE_USER, ROLE_ANONYMOUS
    }

}
