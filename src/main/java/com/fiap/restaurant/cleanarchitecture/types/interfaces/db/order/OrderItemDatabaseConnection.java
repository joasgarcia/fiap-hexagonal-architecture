package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order;

import com.fiap.restaurant.cleanarchitecture.external.db.order.OrderItemJpa;

public interface OrderItemDatabaseConnection<T> {

    T save(OrderItemJpa orderItemJpa);

}
