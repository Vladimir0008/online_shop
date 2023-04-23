package com.hillel.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String login;

    private String password;

    private BigDecimal balance;

    private boolean isBlocked;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    public enum Role {
        ROLE_ADMIN, ROLE_USER, ROLE_ANONYMOUS
    }

}
