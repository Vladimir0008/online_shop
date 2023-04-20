package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.CartDTO;
import com.hillel.online_shop.dto.OrderDTO;
import com.hillel.online_shop.dto.ProductInfoDTO;
import com.hillel.online_shop.dto.user.UserDTO;
import com.hillel.online_shop.dto.user.UserInfoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    void addToCart(CartDTO cartDTO, ProductInfoDTO productInfoDTO);

    void removeFromCart(CartDTO cartDTO, ProductInfoDTO productInfoDTO);

    void clearCart(CartDTO cartDTO);

    CartDTO getCart(Long userId);

    Long createOrder(UserDTO userDTO, CartDTO cartDTO);

    List<OrderDTO> getAllOrders(Long userId);

    void setBalance(Long userId, BigDecimal balance);

    void updateAccountInfo(UserDTO userDTO);

    UserInfoDTO getAccountInfo(Long userId);
}
