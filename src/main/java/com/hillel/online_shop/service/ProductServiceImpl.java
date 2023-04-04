package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.entity.Product;
import com.hillel.online_shop.exception.ProductNotFoundException;
import com.hillel.online_shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long create(ProductDTO productDTO) {
        if(productRepository.existsById(productDTO.getId())) {
            throw new DuplicateKeyException("product with id " + productDTO.getId() + " already exists");
        }

        return productRepository.save(map(productDTO)).getId();
    }

    @Override
    public void update(ProductDTO productDTO) {
        findById(productDTO.getId());
        productRepository.save(map(productDTO));
    }

    @Override
    public void delete(long id) {
        findById(id);
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getById(long id) {
        return map(findById(id));
    }

    @Override
    public List<ProductDTO> getAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(this::map)
                .collect(Collectors.toList());
    }

    private Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("product with id " + id + " not found"));
    }

    private ProductDTO map(Product product) {
        return map(product, ProductDTO.class);
    }

    private Product map(ProductDTO productDTO) {
        return map(productDTO, Product.class);
    }

    private <T, U> T map(U source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}