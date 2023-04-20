package com.hillel.online_shop.dto;

import com.hillel.online_shop.dto.user.UserDTO;
import com.hillel.online_shop.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;

    private BigDecimal cost;

    private LocalDate date;

    private List<ProductInfoDTO> productInfos;

    private UserDTO user;
}
