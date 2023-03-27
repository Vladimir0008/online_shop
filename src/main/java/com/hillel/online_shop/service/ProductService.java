package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(long id);
    List<Product> getAllProducts();
    void createProduct(ProductDTO productDTO);
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(long id);
}
