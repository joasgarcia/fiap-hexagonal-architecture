package com.fiap.restaurant.cleanarchitecture.gateway.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.ItemProduct;

public interface IItemProductGateway {

    ItemProduct save(ItemProduct itemProduct);

    void delete(Long id);
}
