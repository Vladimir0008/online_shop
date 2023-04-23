package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.cart.CartRequestDTO;
import com.hillel.online_shop.dto.cart.CartResponseDTO;
import com.hillel.online_shop.dto.order.OrderRequestDTO;
import com.hillel.online_shop.dto.product.ProductDTO;
import com.hillel.online_shop.dto.product.PurchaseDTO;
import com.hillel.online_shop.dto.user.UserRequestDTO;
import com.hillel.online_shop.dto.user.UserResponseDTO;
import com.hillel.online_shop.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final PurchaseService productInfoService;
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    public void addToCart(Long userId, ProductDTO productDTO) {
        ProductDTO byId = productService.getById(productDTO.getId());
        if (byId.getQuantity() < productDTO.getQuantity()) {
            throw new IllegalArgumentException("Not enough product quantity in storage"); //check quantity
        }

        CartRequestDTO cartRequestDTO = cartService.getByUserId(userId); //get cart
        List<PurchaseDTO> purchases = cartRequestDTO.getPurchases();

        PurchaseDTO purchaseDTO = modelMapper.map(productDTO, PurchaseDTO.class);
        if (purchases.contains(purchaseDTO)) {
            PurchaseDTO purchase = purchases.get(purchases.indexOf(purchaseDTO));
            int newQuantity = purchase.getQuantity() + purchaseDTO.getQuantity();

            if (byId.getQuantity() < newQuantity) {
                throw new IllegalArgumentException("Not enough product quantity in storage"); //check quantity
            }
            purchase.setQuantity(newQuantity);
            productInfoService.update(purchase);
        } else {
            purchases.add(purchaseDTO);
            productInfoService.create((purchaseDTO), cartRequestDTO.getId());
        } // todo check product existence in cart and increment quantity
        //todo set price to cart

        cartService.update(cartRequestDTO);
    }

    public void removeFromCart(CartRequestDTO cartDTO, PurchaseDTO purchaseDTO) {
        List<PurchaseDTO> products = cartDTO.getPurchases();
        products.remove(modelMapper.map(purchaseDTO, PurchaseDTO.class));
        cartService.update(cartDTO);
    }

    public void clearCart(CartRequestDTO cartDTO) {
        List<PurchaseDTO> products = cartDTO.getPurchases();
        products.clear();
        cartService.update(cartDTO);
    }

    public CartResponseDTO getCart(Long userId) {
        CartRequestDTO cartRequestDTO = cartService.getByUserId(userId);
        List<PurchaseDTO> products = productInfoService.findAllByCartId(cartRequestDTO.getId());
        cartRequestDTO.setPurchases(products);
        return modelMapper.map(cartRequestDTO, CartResponseDTO.class);
    }

    public Long createOrder(UserRequestDTO userDTO, CartRequestDTO cartDTO) {
        OrderRequestDTO orderDTO = new OrderRequestDTO();
        orderDTO.setUser(userDTO);
        return orderService.create(orderDTO);
    }

    public List<OrderRequestDTO> getAllOrders(Long userId) {
        return userService.findById(userId).getOrders();
    }

    public void setBalance(Long userId, BigDecimal balance) {
        UserResponseDTO byId = userService.findById(userId);
        byId.setBalance(balance);
        userService.update(modelMapper.map(byId, UserRequestDTO.class));
    }

    public void updateAccountInfo(UserRequestDTO userRequestDTO) {
        userService.update(userRequestDTO);
    }

    public UserResponseDTO getAccountInfo(Long userId) {
        return userService.findById(userId);
    }
}
