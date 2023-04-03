package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getById(long id);

    List<ProductDTO> getAll();

    Long create(ProductDTO productDTO);

    Long update(ProductDTO productDTO);

    void delete(long id);
}

