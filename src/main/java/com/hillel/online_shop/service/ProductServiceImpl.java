package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.entity.Product;
import com.hillel.online_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;


    @Override
    public Product getProductById(long id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public void createProduct(ProductDTO productDTO) {

    }

    @Override
    public void updateProduct(ProductDTO productDTO) {

    }

    @Override
    public void deleteProduct(long id) {

    }
}
