package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.order.OrderDTO;

public interface OrderService {
    OrderDTO getById(long id);

    Long create(OrderDTO orderDTO);

    void update(OrderDTO orderDTO);

    void delete(long id);

}
