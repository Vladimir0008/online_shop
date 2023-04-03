package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getById(long id);

    List<UserDTO> getAll();

    Long create(UserDTO userDTO);

    Long update(UserDTO userDTO);

    void delete(long id);
}

