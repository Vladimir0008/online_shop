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
        cartService.add(getCartId(), productDTO);
    }

    @DeleteMapping("/remove-from-cart/{productId}")
    public void removeFromCart(@PathVariable Long productId) {
        cartService.remove(getCartId(), productId);
    }

    @PutMapping("/reduce-product-from-cart")
    public void reduceProductFromCart(@RequestBody ProductDTO productDTO) {
        cartService.reduceQuantity(getCartId(), productDTO);
    }

    @GetMapping("/get-cart")
    public CartDTO getCart() {
        return cartService.getById(getCartId());
    }

    @DeleteMapping("/clear-cart")
    public void clearCart() {
        cartService.clear(getCartId());
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        return userService.findByLogin(login).getId();
    }

    private Long getCartId() {
        return cartService.getByUserId(getCurrentUserId()).getId();
    }
}
