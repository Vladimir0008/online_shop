package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.CartDTO;
import com.hillel.online_shop.dto.user.UserDTO;

public interface CartService {
    CartDTO getById(long id);

    Long create(UserDTO userDTO);

    void update(CartDTO cartDTO);
}
