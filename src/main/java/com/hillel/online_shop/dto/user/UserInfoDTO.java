package com.hillel.online_shop.dto.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserInfoDTO {

    private String firstName;

    private String lastName;

    private int age;

    private String login;

    private String email;

    private BigDecimal balance;

}
