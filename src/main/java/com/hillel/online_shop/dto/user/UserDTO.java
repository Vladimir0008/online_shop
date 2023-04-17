package com.hillel.online_shop.dto.user;

import com.hillel.online_shop.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class UserDTO {

    private Long id;

    @Size(min = 3, message = "Firstname must be longer than three!")
    private String firstName;

    @Size(min = 3, message = "Lastname must be longer than three!")
    private String lastName;

    @Size(min = 3, message = "Login must be longer than three!")
    private String login;

    @Size(min = 8, message = "Password must be longer than three!")
    private String password;

    @Positive(message = "Age must be greater than zero!")
    private int age;

    @Email
    private String email;

    private User.Role role;

}
