package com.fiap.restaurant.core.repository.order;

import com.fiap.restaurant.core.model.order.ItemProduct;

public interface IItemProductRepository {

    ItemProduct save(ItemProduct itemProduct);

}
