package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.CartDTO;
import com.hillel.online_shop.dto.user.UserDTO;
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
    public CartDTO getById(long id) {
        return modelMapper.map(cartRepository.findById(id), CartDTO.class);
    }

    @Override
    public Long create(UserDTO userDTO) {
        return cartRepository.save(modelMapper.map(userDTO, Cart.class)).getId();
    }

    @Override
    public void update(CartDTO cartDTO) {
        cartRepository.save(modelMapper.map(cartDTO, Cart.class));
    }
}
