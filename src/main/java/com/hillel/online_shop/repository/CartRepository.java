package com.hillel.online_shop.repository;

import com.hillel.online_shop.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
}
