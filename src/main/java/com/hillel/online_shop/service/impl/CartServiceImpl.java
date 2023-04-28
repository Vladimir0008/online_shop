package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.cart.CartDTO;
import com.hillel.online_shop.dto.product.ProductDTO;
import com.hillel.online_shop.entity.Cart;
import com.hillel.online_shop.entity.Product;
import com.hillel.online_shop.entity.Purchase;
import com.hillel.online_shop.exception.ProductNotFoundException;
import com.hillel.online_shop.exception.UserNotFoundException;
import com.hillel.online_shop.repository.CartRepository;
import com.hillel.online_shop.repository.ProductRepository;
import com.hillel.online_shop.repository.PurchaseRepository;
import com.hillel.online_shop.repository.UserRepository;
import com.hillel.online_shop.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public CartDTO getById(long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setCost(getCost(cart));
        return cartDTO;
    }

    @Override
    public CartDTO getByUserId(long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setCost(getCost(cart));
        return cartDTO;
    }

    @Override
    public Long create(Long userId) {
        Cart cart = new Cart();
        cart.setUser(userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("user with id " + userId + "not found")));
        return cartRepository.save(cart).getId();
    }

    @Override
    public void update(CartDTO cartDTO) {
        cartRepository.save(modelMapper.map(cartDTO, Cart.class));
    }

    @Override
    public void add(Long cartId, ProductDTO productDTO) {
        Cart cart = findCartById(cartId);

        Product product = findProductById(productDTO.getId());
        Optional<Purchase> purchaseOpt = purchaseRepository.findByProductIdAndCartId(product.getId(), cartId);

        Purchase purchase;
        if (purchaseOpt.isPresent()) {
            purchase = purchaseOpt.get();
            purchase.setQuantity(purchase.getQuantity() + productDTO.getQuantity());
            BigDecimal newPrice = product.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity()));
            purchase.setPrice(newPrice);
            System.out.println("add if exist " + newPrice);
        } else {
            purchase = new Purchase();
            purchase.setQuantity(productDTO.getQuantity());
            purchase.setName(product.getName());
            purchase.setPrice(product.getPrice().multiply(BigDecimal.valueOf(productDTO.getQuantity())));
            purchase.setProduct(product);
            purchase.setCart(cart);
//            cart.getPurchases().add(purchase);
            System.out.println("add if not exist" + purchase.getPrice());
        }
        purchaseRepository.save(purchase);
//        cart.setCost(getCost(cart));
//        cartRepository.save(cart);
    }


//    @Transactional
//    public void remove(Long cartId, ProductDTO productDTO) {
//        BigDecimal price = findProductById(productDTO.getId()).getPrice();
//        Optional<Purchase> purchaseOpt = purchaseRepository.findByProductIdAndCartId(productDTO.getId(), cartId);
//        Cart cart = findCartById(cartId);
//
//        if (purchaseOpt.isPresent()) {
//            if (productDTO.getQuantity() > purchaseOpt.get().getQuantity()) {
//                throw new IllegalArgumentException("not enough quantity to remove");
//            }
//            if (productDTO.getQuantity() == null || productDTO.getQuantity() == purchaseOpt.get().getQuantity()) {
//                cart.getPurchases().remove(purchaseOpt.get());
//                purchaseRepository.delete(purchaseOpt.get());
//            } else {
//                Purchase purchase = purchaseOpt.get();
//                purchase.setQuantity(purchase.getQuantity() - productDTO.getQuantity());
//                BigDecimal newPrice = price.multiply(BigDecimal.valueOf(purchase.getQuantity()));
//                purchase.setPrice(newPrice);
//                purchaseRepository.save(purchase);
//            }
//        }
//        cart.setCost(getCost(cart));
//        cartRepository.save(cart); }

    @Override
    public void remove(Long cartId, Long productId) {
        Purchase purchase = purchaseRepository.findByProductIdAndCartId(productId, cartId)
                .orElseThrow(EntityNotFoundException::new);
        purchaseRepository.delete(purchase);
    }

    @Override
    public void reduceQuantity(Long cartId, ProductDTO productDTO) {
        Purchase purchase = purchaseRepository.findByProductIdAndCartId(productDTO.getId(), cartId)
                .orElseThrow(EntityNotFoundException::new);
        BigDecimal price = findProductById(productDTO.getId()).getPrice();

        if (productDTO.getQuantity() >= purchase.getQuantity()) {
            throw new IllegalArgumentException("Error: Unable to reduce quantity. " +
                    "Available quantity of " + purchase.getName() + " is " + purchase.getQuantity() +
                    ". Please reduce the quantity to " + (purchase.getQuantity() - 1));
        }

        purchase.setQuantity(purchase.getQuantity() - productDTO.getQuantity());
        BigDecimal newPrice = price.multiply(BigDecimal.valueOf(purchase.getQuantity()));
        purchase.setPrice(newPrice);
        purchaseRepository.save(purchase);
    }

    @Override
    public void clear(Long cartId) {
        purchaseRepository.deleteAllByCartId(cartId);
    }


    private BigDecimal getCost(Cart cart) {
        List<Purchase> purchases = cart.getPurchases();
        return purchases.stream()
                .map(Purchase::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    private Cart findCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Cart with id " + id + " not found"));
    }
}
