package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.service.ProductService;
import com.hillel.online_shop.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/products")
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping("/product")
    public Long save(@RequestBody ProductDTO productDTO) {

        return productService.createProduct(productDTO);
    }

    @GetMapping
    public String ping() {
        return "Ping";
    }
}
