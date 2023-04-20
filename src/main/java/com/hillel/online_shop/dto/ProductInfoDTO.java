package com.hillel.online_shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoDTO {
    private Long id;

    private BigDecimal price;

    private int quantity;
}
