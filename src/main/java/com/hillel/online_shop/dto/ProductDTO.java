package com.hillel.online_shop.dto;

import com.hillel.online_shop.entity.Cart;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private int quantity;
}
