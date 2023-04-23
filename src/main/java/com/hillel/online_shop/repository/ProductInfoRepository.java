package com.hillel.online_shop.repository;

import com.hillel.online_shop.entity.Purchase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductInfoRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findAllByCartId(Long cartId);
}
