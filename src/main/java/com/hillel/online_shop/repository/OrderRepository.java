package com.hillel.online_shop.repository;

import com.hillel.online_shop.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
