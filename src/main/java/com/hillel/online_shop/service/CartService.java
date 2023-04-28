package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.cart.CartDTO;
import com.hillel.online_shop.dto.product.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

public interface CartService {
    CartDTO getById(long id);

    CartDTO getByUserId(long userId);

    Long create(Long userId);

    void update(CartDTO cartDTO);

    void add(Long cartId, ProductDTO productDTO);

    void reduceQuantity(Long cartId, ProductDTO productDTO);
    void remove(Long cartId, Long productId);

    void clear(Long cartId);
}
