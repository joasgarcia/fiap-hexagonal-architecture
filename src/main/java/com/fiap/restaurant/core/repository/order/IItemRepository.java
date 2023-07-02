package com.fiap.restaurant.core.repository.order;

import com.fiap.restaurant.core.model.order.Item;

public interface IItemRepository {

    Item save(Item item);
}
