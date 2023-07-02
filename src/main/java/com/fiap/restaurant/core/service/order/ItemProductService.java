package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.core.model.order.ItemProduct;
import com.fiap.restaurant.core.repository.order.IItemProductRepository;

public class ItemProductService {

    private final IItemProductRepository itemProductRepository;

    public ItemProductService(IItemProductRepository itemProductRepository) {
        this.itemProductRepository = itemProductRepository;
    }

    public ItemProduct save(ItemProduct itemProduct) {

        return this.itemProductRepository.save(itemProduct);
    }
}
