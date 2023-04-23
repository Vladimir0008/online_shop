package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.order.OrderRequestDTO;

public interface OrderService {
    OrderRequestDTO getById(long id);

    Long create(OrderRequestDTO orderDTO);

    void update(OrderRequestDTO orderDTO);

    void delete(long id);

}
