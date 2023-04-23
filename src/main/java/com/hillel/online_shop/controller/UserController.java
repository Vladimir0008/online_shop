package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.user.UserRequestDTO;
import com.hillel.online_shop.dto.user.UserResponseDTO;
import com.hillel.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/users")
public class UserController {
    private final UserService<UserRequestDTO, UserResponseDTO> userService;

    @GetMapping("/get-by-username/{username}")
    public UserResponseDTO getByLogin(@PathVariable String login) {
        return userService.findByLogin(login);
    }

    @PostMapping("/create")
    public Long create(@Validated @RequestBody UserRequestDTO userRequestDTO) {
        return userService.create(userRequestDTO);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @Validated @RequestBody UserRequestDTO userRequestDTO) {
        userService.findById(id);
        userRequestDTO.setId(id);
        userService.update(userRequestDTO);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/get/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/get-all")
    public List<UserResponseDTO> getUsers() {
        return userService.findAll();
    }
}
