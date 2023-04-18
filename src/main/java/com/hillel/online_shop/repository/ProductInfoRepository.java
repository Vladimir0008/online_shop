package com.hillel.online_shop.repository;

import com.hillel.online_shop.entity.ProductInfo;
import org.springframework.data.repository.CrudRepository;

public interface ProductInfoRepository extends CrudRepository<ProductInfo, Long> {
}
