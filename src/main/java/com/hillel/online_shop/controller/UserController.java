package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.user.UserDTO;
import com.hillel.online_shop.dto.user.UserInfoDTO;
import com.hillel.online_shop.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/get-by-name/{name}")
    public UserDetails getUserDetails(@PathVariable String name) {
        return userServiceImpl.loadUserByUsername(name);
    }

    @PostMapping("/create")
    public Long create(@Validated @RequestBody UserDTO userDTO) {
        return userServiceImpl.create(userDTO);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @Validated @RequestBody UserDTO userDTO) {
        UserDTO existUser = userServiceImpl.getById(id);

        if (existUser != null) {
            userDTO.setId(id);
            userServiceImpl.update(userDTO);
        }
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(value = "id") Long id) {
        userServiceImpl.delete(id);
    }

    @GetMapping("/get/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userServiceImpl.getById(id);
    }

    @GetMapping("/get-info/{id}")
    public UserInfoDTO getInfoById(@PathVariable Long id) {
        return userServiceImpl.getInfoById(id);
    }

    @GetMapping("/get-all")
    public List<UserDTO> getUsers() {
        return userServiceImpl.getAll();
    }

    @GetMapping("/get-all-info")
    public List<UserInfoDTO> getUsersInfo() {
        return userServiceImpl.getAllInfo();
    }
}
