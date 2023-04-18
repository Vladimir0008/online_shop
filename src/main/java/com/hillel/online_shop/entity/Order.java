package com.hillel.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal cost;

    private LocalDate date;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<ProductInfo> productInfos;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
