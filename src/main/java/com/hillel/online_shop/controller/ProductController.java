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

        return productService.createProduct(productDTO);
    }

    @DeleteMapping(value = "/delete")
    public void delete(@RequestParam(value = "id") Long id) {

        productService.deleteProduct(id);
    }

    @GetMapping(value = "/get_product/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {

        return productService.getProductById(id);
    }

    @GetMapping(value = "/get_products")
    public List<ProductDTO> getProducts() {

        return productService.getAllProducts();
    }

    @PostMapping("/update")
    public Long update(@RequestBody ProductDTO productDTO) {

        return productService.updateProduct(productDTO);
    }
}
