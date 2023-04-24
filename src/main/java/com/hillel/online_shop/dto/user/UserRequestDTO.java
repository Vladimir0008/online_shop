package com.hillel.online_shop.dto.user;

import com.hillel.online_shop.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRequestDTO {

    private Long id;

    @Size(min = 3, message = "Firstname must be longer than three!")
    private String firstName;

    @Size(min = 3, message = "Lastname must be longer than three!")
    private String lastName;

    @Size(min = 3, message = "Login must be longer than three!")
    private String login;

    @Size(min = 3, message = "Password must be longer than three!")
    private String password;

    @Positive(message = "Age must be greater than zero!")
    private int age;

    @Email
    private String email;

    private BigDecimal balance;

    private User.Role role;
}
