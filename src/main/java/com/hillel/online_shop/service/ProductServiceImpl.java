package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.ProductDTO;
import com.hillel.online_shop.entity.Product;
import com.hillel.online_shop.exception.ProductNotFoundException;
import com.hillel.online_shop.repository.ProductRepository;
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
    public ProductDTO getById(long id) {
        return mapProduct(findById(id));
    }

    @Override
    public List<ProductDTO> getAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(ProductDTO productDTO) {
        return productRepository.save(mapProduct(productDTO)).getId();
    } // TODO: 28.03.23 add checking of product existence

    @Override
    public Long update(ProductDTO productDTO) {
        return productRepository.save(mapProduct(productDTO)).getId();
    } // TODO: 28.03.23 add checking of product existence

    @Override
    public void delete(long id) {
        findById(id);
        productRepository.deleteById(id);
    }

    private ProductDTO mapProduct(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product mapProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    private Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found!"));
    }
}
