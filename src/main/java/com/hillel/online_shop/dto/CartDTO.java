package com.hillel.online_shop.dto;

import com.hillel.online_shop.dto.user.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {

    private Long id;

    private List<ProductDTO> products;

    private UserDTO user;
}
