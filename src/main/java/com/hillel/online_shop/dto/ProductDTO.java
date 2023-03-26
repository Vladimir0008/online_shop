package com.hillel.online_shop.dto;

import com.hillel.online_shop.entity.Cart;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private int quantity;

    private CartDTO cart;
}
