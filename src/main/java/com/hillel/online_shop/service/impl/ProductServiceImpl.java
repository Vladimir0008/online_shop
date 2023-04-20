package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.entity.Product;
import com.hillel.online_shop.exception.ProductNotFoundException;
import com.hillel.online_shop.repository.ProductRepository;
import com.hillel.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        if (productDTO.getId() != null) {
            throw new IllegalArgumentException("field \"id\" must be null");
        }

        return productRepository.save(modelMapper.map(productDTO,Product.class)).getId();
    }

    @Override
    public void update(ProductDTO productDTO) {
        if(productDTO.getId() == null) {
            throw new IllegalArgumentException("field \"id\" must not be null");
        }
        findById(productDTO.getId());
        productRepository.save(modelMapper.map(productDTO,Product.class));
    }

    @Override
    public void delete(long id) {
        findById(id);
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getById(long id) {
        return modelMapper.map(findById(id), ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(product -> modelMapper.map(product, ProductDTO.class))
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