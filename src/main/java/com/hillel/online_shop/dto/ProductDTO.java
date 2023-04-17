package com.hillel.online_shop.dto;

import com.hillel.online_shop.entity.Cart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    @Size(min = 3, message = "Name must be longer than three!")
    private String name;

    @Positive(message = "Price must be greater than zero!")
    private BigDecimal price;

    @Range(min = 0, message = "Quantity must be greater or equal zero!")
    private int quantity;
}
