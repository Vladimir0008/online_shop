package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.cart.CartResponseDTO;
import com.hillel.online_shop.dto.product.PurchaseDTO;
import com.hillel.online_shop.dto.user.UserRequestDTO;
import com.hillel.online_shop.dto.user.UserResponseDTO;
import com.hillel.online_shop.service.UserService;
import com.hillel.online_shop.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final UserService<UserRequestDTO, UserResponseDTO> userService;

    @PostMapping("/add-to-cart/{id}")
    public void addToCart(@PathVariable Long id, @RequestBody PurchaseDTO purchaseDTO) {
        customerService.addToCart(getCurrentUserId(), , id);
    }

    @GetMapping("/get-cart")
    public CartResponseDTO getCart() {
        return customerService.getCart(getCurrentUserId());
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        return userService.findByLogin(login).getId();
    }
}
