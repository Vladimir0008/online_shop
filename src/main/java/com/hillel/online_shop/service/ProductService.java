package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(long id);
    List<ProductDTO> getAllProducts();
    Long createProduct(ProductDTO productDTO);
    Long updateProduct(ProductDTO productDTO);
    void deleteProduct(long id);
}
