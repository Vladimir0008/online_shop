package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.CartDTO;
import com.hillel.online_shop.dto.OrderDTO;
import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.dto.ProductInfoDTO;
import com.hillel.online_shop.dto.user.UserDTO;
import com.hillel.online_shop.dto.user.UserInfoDTO;
import com.hillel.online_shop.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserServiceImpl userService;
//    private final ProductInfoService productInfoService;
    private final ModelMapper modelMapper;

    @Override
    public void addToCart(CartDTO cartDTO, ProductInfoDTO productInfoDTO) {
        ProductDTO byId = productService.getById(productInfoDTO.getId());

        if(byId.getQuantity() < productInfoDTO.getQuantity()) {
            throw new RuntimeException("Not enough product quantity in storage");
        }

        List<ProductInfoDTO> productDTOS = cartDTO.getProducts();

        if(productDTOS.contains(productInfoDTO)) {
            ProductInfoDTO productInfo = productDTOS.get(productDTOS.indexOf(productInfoDTO));
            int newQuantity = productInfo.getQuantity() + productInfoDTO.getQuantity();
            productInfo.setQuantity(newQuantity);
        } else {
        productDTOS.add(productInfoDTO);
        }
        // TODO: 20.04.23 save to DB
    }

    @Override
    public void removeFromCart(CartDTO cartDTO, ProductInfoDTO productInfoDTO) {
        List<ProductInfoDTO> products = cartDTO.getProducts();
        products.remove(productInfoDTO);
        // TODO: 20.04.23 save to DB
    }

    @Override
    public void clearCart(CartDTO cartDTO) {
        cartDTO.setProducts(null);
        // TODO: 20.04.23 save to DB
    }

    @Override
    public CartDTO getCart(Long userId) {
        return userService.getById(userId).getCartDTO();
    }

    @Override
    public Long createOrder(UserDTO userDTO, CartDTO cartDTO) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser(userDTO);
        orderService.create(orderDTO);
        return orderService.create(orderDTO);
    }

    @Override
    public List<OrderDTO> getAllOrders(Long userId) {
        return userService.getById(userId).getOrder();
    }

    @Override
    public void setBalance(Long userId, BigDecimal balance) {
        UserDTO byId = userService.getById(userId);
        byId.setBalance(balance);
        userService.update(byId);
    }

    @Override
    public void updateAccountInfo(UserDTO userDTO) {
        userService.update(userDTO);
    }

    @Override
    public UserInfoDTO getAccountInfo(Long userId) {
        return userService.getInfoById(userId);
    }

    // TODO: 20.04.23 get authenticated user id
}
