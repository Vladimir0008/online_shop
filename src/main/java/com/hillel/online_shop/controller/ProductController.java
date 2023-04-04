package com.hillel.online_shop.controller;

import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/create")
    public Long create(@RequestBody ProductDTO productDTO) {
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

    @PostMapping("/update")
    public void update(@RequestBody ProductDTO productDTO) {
        productService.update(productDTO);
    }
}
