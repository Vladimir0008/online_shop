package com.hillel.online_shop.dto.user;

import com.hillel.online_shop.dto.order.OrderRequestDTO;
import com.hillel.online_shop.entity.Cart;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    private String login;

    private String email;

    private BigDecimal balance;

    private Cart cart;

    private List<OrderRequestDTO> orders;

}
