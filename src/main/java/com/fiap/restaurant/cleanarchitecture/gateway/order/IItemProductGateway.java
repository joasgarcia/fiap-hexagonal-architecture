package com.fiap.restaurant.cleanarchitecture.gateway.order;

import com.fiap.restaurant.entity.order.ItemProduct;

import java.util.List;

public interface IItemProductGateway {

    ItemProduct save(ItemProduct itemProduct);
    void delete(ItemProduct itemProduct);
    List<ItemProduct> findAllByItemId(Long itemId);
}
