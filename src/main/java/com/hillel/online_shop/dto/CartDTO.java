package com.hillel.online_shop.dto;

import com.hillel.online_shop.dto.user.UserDTO;
import com.hillel.online_shop.entity.ProductInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO {

    private Long id;

    private BigDecimal cost;

    private List<ProductInfoDTO> products;

    private UserDTO user;
}
