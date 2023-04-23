package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.cart.CartRequestDTO;
import com.hillel.online_shop.entity.Cart;
import com.hillel.online_shop.repository.CartRepository;
import com.hillel.online_shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;


    @Override
    public CartRequestDTO getById(long id) {
        return modelMapper.map(cartRepository.findById(id), CartRequestDTO.class);
    }

    @Override
    public CartRequestDTO getByUserId(long userId) {
        return modelMapper.map(cartRepository.findByUserId(userId), CartRequestDTO.class);
    }

    @Override
    public Long create(CartRequestDTO cartRequestDTO) {
        return cartRepository.save(modelMapper.map(cartRequestDTO, Cart.class)).getId();
    }

    @Override
    public void update(CartRequestDTO cartRequestDTO) {
        cartRepository.save(modelMapper.map(cartRequestDTO, Cart.class));
    }
}
