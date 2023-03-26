package com.hillel.online_shop.dto;

import com.hillel.online_shop.entity.Cart;
import jakarta.persistence.OneToOne;

public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    private String email;

}
