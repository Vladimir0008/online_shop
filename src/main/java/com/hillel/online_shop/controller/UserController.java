package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.user.UserInfoDTO;
import com.hillel.online_shop.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/get/{id}")
    public UserInfoDTO getUser(@PathVariable Long id) {
        return userServiceImpl.getInfoById(id);
    }
}
