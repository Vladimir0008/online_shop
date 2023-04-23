package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.order.OrderRequestDTO;
import com.hillel.online_shop.repository.OrderRepository;
import com.hillel.online_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderRequestDTO getById(long id) {
        return null;
    }

    @Override
    public Long create(OrderRequestDTO orderRequestDTO) {
        return null;
    }

    @Override
    public void update(OrderRequestDTO orderRequestDTO) {

    }

    @Override
    public void delete(long id) {

    }
}
