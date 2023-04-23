package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.cart.CartRequestDTO;

public interface CartService {
    CartRequestDTO getById(long id);

    CartRequestDTO getByUserId(long userId);

    Long create(CartRequestDTO cartDTO);

    void update(CartRequestDTO cartDTO);
}
