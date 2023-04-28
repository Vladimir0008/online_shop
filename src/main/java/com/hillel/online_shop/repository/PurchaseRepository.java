package com.hillel.online_shop.repository;

import com.hillel.online_shop.entity.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    Optional<Purchase> findByProductIdAndCartId(Long productId, Long cartId);
    List<Purchase> findAllByCartId(Long cartId);
    Optional<Purchase> findByProductId(Long productId);
    @Transactional
    void deleteAllByCartId(Long cartId);
}
