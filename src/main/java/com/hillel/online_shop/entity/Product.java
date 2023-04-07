package com.hillel.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private UserOrder userOrder;
    // TODO: 07.04.23 cascade type
}
