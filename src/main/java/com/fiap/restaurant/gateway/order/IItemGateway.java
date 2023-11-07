package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.Item;

public interface IItemGateway {

    Item save(Item item);

    void delete(Long id);

    boolean existsById(Long id);

    Item getById(Long id);
}
