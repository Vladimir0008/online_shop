package com.hillel.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal cost;

    private LocalDate date;

    @OneToMany(mappedBy = "userOrder", fetch = FetchType.LAZY)
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
