package com.hillel.online_shop.dto.cart;

import com.hillel.online_shop.dto.product.PurchaseDTO;
import com.hillel.online_shop.dto.user.UserRequestDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartRequestDTO {

    private Long id;

    private BigDecimal cost;

    private List<PurchaseDTO> purchases;

    private UserRequestDTO user;
}
