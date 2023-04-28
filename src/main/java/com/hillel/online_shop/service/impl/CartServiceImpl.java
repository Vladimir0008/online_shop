package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.cart.CartDTO;
import com.hillel.online_shop.dto.product.ProductDTO;
import com.hillel.online_shop.entity.Cart;
import com.hillel.online_shop.entity.Product;
import com.hillel.online_shop.entity.Purchase;
import com.hillel.online_shop.exception.CartNotFoundException;
import com.hillel.online_shop.exception.ProductNotFoundException;
import com.hillel.online_shop.exception.UserNotFoundException;
import com.hillel.online_shop.repository.CartRepository;
import com.hillel.online_shop.repository.ProductRepository;
import com.hillel.online_shop.repository.PurchaseRepository;
import com.hillel.online_shop.repository.UserRepository;
import com.hillel.online_shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public CartDTO getById(long id) {
        return modelMapper.map(cartRepository.findById(id), CartDTO.class);
    }

    @Override
    public CartDTO getByUserId(long userId) {
        return modelMapper.map(cartRepository.findByUserId(userId), CartDTO.class);
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
    @Transactional
    public void add(Long cartId, ProductDTO productDTO) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new RuntimeException("Cart with id " + cartId + "not found"));

        Long productId = productDTO.getId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        Optional<Purchase> purchaseOpt = purchaseRepository.findByProductIdAndCartId(productId, cartId);

        Purchase purchase;
        if (purchaseOpt.isPresent()) {
            purchase = purchaseOpt.get();
            purchase.setQuantity(purchase.getQuantity() + productDTO.getQuantity());
            BigDecimal newPrice = product.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity()));
            purchase.setPrice(newPrice);
        } else {
            purchase = new Purchase();
            purchase.setQuantity(productDTO.getQuantity());
            purchase.setName(product.getName());
            purchase.setPrice(product.getPrice().multiply(new BigDecimal(purchase.getQuantity())));
            purchase.setProduct(product);
            purchase.setCart(cart);
        }
        purchaseRepository.save(purchase);
        updateCost(cartId);
    }

    @Override
    @Transactional
    public void remove(Long cartId, ProductDTO productDTO) {
        Optional<Purchase> purchaseOpt = purchaseRepository.findByProductIdAndCartId(productDTO.getId(), cartId);

//        if (productDTO.getQuantity() == null ) {
//            purchaseOpt.ifPresent(purchaseRepository::delete);
//        } else if (purchaseOpt.isPresent()) {
//            Purchase purchase = purchaseOpt.get();
//            purchase.setQuantity(purchase.getQuantity() - productDTO.getQuantity());
//            purchaseRepository.save(purchase);
//        }

        if (purchaseOpt.isPresent()) {
            Purchase purchase = purchaseOpt.get();
            Product product = productRepository.findById(productDTO.getId())
                    .orElseThrow(()-> new ProductNotFoundException("Can't find product with id " + productDTO.getId()));

            if (purchase.getQuantity() <= productDTO.getQuantity()) {
                purchase.setQuantity(0);
                purchase.setPrice(BigDecimal.ZERO);
                purchaseRepository.save(purchase);

                //purchaseRepository.deleteById(purchase.getId());
            } else {
                purchase.setQuantity(purchase.getQuantity() - productDTO.getQuantity());
                purchase.setPrice(product.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity())));
                purchaseRepository.save(purchase);
            }

            updateCost(cartId);
            updatePurchases();
        } else {
            throw new ProductNotFoundException("Can't find product with id " + productDTO.getId() + " in cart with id " + cartId);
        }
    }

    @Override
    public void clear(Long cartId) {
        purchaseRepository.deleteAllByCartId(cartId);
    }

    // TODO: 24.04.23 implement cost update in cart

    private void updateCost(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Can't find cart with id " + cartId));

        List<Purchase> purchases = purchaseRepository.findAllByCartId(cartId);

        BigDecimal sumCost = purchases.stream()
                .map(Purchase::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setCost(sumCost);
        cartRepository.save(cart);
    }

    private void updatePurchases() {
        List<Purchase> purchases = (List<Purchase>)purchaseRepository.findAll();

        List<Purchase> purchaseList = purchases.stream()
                .filter(purchase -> Objects.equals(purchase.getQuantity(), 0))
                .collect(Collectors.toList());

        purchaseRepository.deleteAll(purchaseList);
    }
}
