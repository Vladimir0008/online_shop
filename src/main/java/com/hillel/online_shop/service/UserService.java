package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.user.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getById(long id);

    List<UserDTO> getAll();

    Long create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(long id);
}

