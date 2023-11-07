package com.fiap.restaurant.types.interfaces.db.order;

import com.fiap.restaurant.external.db.order.OrderItemJpa;

public interface OrderItemDatabaseConnection<T> {

    T save(OrderItemJpa orderItemJpa);

}
