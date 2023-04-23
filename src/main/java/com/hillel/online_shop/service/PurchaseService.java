package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.product.PurchaseDTO;

import java.util.List;

public interface PurchaseService {
    PurchaseDTO getById(long id);

    List<PurchaseDTO> getAll();

    List<PurchaseDTO> findAllByCartId(Long cartId);

    Long create(PurchaseDTO purchaseDTO, Long cartId);

    void update(PurchaseDTO purchaseDTO);

    void delete(long id);
}
