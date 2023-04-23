package com.hillel.online_shop.dto.order;

import com.hillel.online_shop.dto.product.PurchaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponseDto {

    private BigDecimal cost;

    private LocalDate date;

    private List<PurchaseDTO> purchases;
}
