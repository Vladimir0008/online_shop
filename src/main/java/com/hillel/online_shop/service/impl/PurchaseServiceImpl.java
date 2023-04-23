package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.product.PurchaseDTO;
import com.hillel.online_shop.entity.Cart;
import com.hillel.online_shop.entity.Purchase;
import com.hillel.online_shop.exception.ProductNotFoundException;
import com.hillel.online_shop.repository.ProductInfoRepository;
import com.hillel.online_shop.service.CartService;
import com.hillel.online_shop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final ProductInfoRepository productInfoRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @Override
    public PurchaseDTO getById(long id) {
        return modelMapper.map(findById(id), PurchaseDTO.class);
    }

    @Override
    public List<PurchaseDTO> getAll() {
        return StreamSupport.stream(productInfoRepository.findAll().spliterator(), false)
                .map(product -> modelMapper.map(product, PurchaseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDTO> findAllByCartId(Long cartId) {
        List<Purchase> products = productInfoRepository.findAllByCartId(cartId);
        return products.stream()
                .map(product -> modelMapper.map(product, PurchaseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(PurchaseDTO purchaseDTO, Long cartId) {
        Purchase purchase = productInfoRepository.save(modelMapper.map(purchaseDTO, Purchase.class));
        purchase.setCart(modelMapper.map(cartService.getById(cartId), Cart.class));
        return purchase.getId();
    }

    @Override
    public void update(PurchaseDTO purchaseDTO) {
        Cart cart = findById(purchaseDTO.getId()).getCart();
        Purchase purchase = modelMapper.map(purchaseDTO, Purchase.class);
        purchase.setCart(cart);
        productInfoRepository.save(purchase);
    }

    @Override
    public void delete(long id) {
        findById(id);
        productInfoRepository.deleteById(id);
    }

    private Purchase findById(long id) {
        return productInfoRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("product with id " + id + " not found"));
    }
}
