package com.hillel.online_shop.dto.cart;

import com.hillel.online_shop.dto.product.PurchaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDTO {

    private BigDecimal cost;

    private List<PurchaseDTO> purchases;
}
