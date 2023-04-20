package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.OrderDTO;
import com.hillel.online_shop.repository.OrderRepository;
import com.hillel.online_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDTO getById(long id) {
        return null;
    }

    @Override
    public Long create(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void update(OrderDTO orderDTO) {

    }

    @Override
    public void delete(long id) {

    }
}
