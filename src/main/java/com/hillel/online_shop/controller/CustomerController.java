package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.cart.CartDTO;
import com.hillel.online_shop.dto.product.ProductDTO;
import com.hillel.online_shop.dto.user.UserRequestDTO;
import com.hillel.online_shop.dto.user.UserResponseDTO;
import com.hillel.online_shop.service.CartService;
import com.hillel.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/customer")
public class CustomerController {
    private final CartService cartService;
    private final UserService<UserRequestDTO, UserResponseDTO> userService;

    @PostMapping("/add-to-cart")
    public void addToCart(@RequestBody ProductDTO productDTO) {
        cartService.add(cartService.getByUserId(getCurrentUserId()).getId(), productDTO);
    }

    @PutMapping("/remove-from-cart")
    public void removeFromCart(@RequestBody ProductDTO productDTO) {
        cartService.remove(cartService.getByUserId(getCurrentUserId()).getId(), productDTO);
    }

    @GetMapping("/get-cart")
    public CartDTO getCart() {
        return cartService.getByUserId(getCurrentUserId());
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        return userService.findByLogin(login).getId();
    }
}
