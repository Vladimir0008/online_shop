package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.product.ProductDTO;
import com.hillel.online_shop.service.ProductService;
import com.hillel.online_shop.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public Long create(@Validated @RequestBody ProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(value = "id") Long id) {
        productService.delete(id);
    }

    @GetMapping("/get/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/get-all")
    public List<ProductDTO> getProducts() {
        return productService.getAll();
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @Validated @RequestBody ProductDTO productDTO) {
        ProductDTO existProduct = productService.getById(id);
        productDTO.setId(id);
        productService.update(productDTO);
    }
}
