package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.product.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getById(long id);

    List<ProductDTO> getAll();

    Long create(ProductDTO productDTO);

    void update(ProductDTO productDTO);

    void delete(long id);
}

