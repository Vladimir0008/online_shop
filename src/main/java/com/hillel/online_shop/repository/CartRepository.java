package com.hillel.online_shop.repository;

import com.hillel.online_shop.entity.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
