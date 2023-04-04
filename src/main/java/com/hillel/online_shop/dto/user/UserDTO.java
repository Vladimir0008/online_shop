package com.hillel.online_shop.dto.user;

import com.hillel.online_shop.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    private int age;

    private String email;

    private User.Role role;

}
