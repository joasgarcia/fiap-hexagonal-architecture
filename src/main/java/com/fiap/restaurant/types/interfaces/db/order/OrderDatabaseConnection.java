package com.fiap.restaurant.types.interfaces.db.order;

import com.fiap.restaurant.external.db.order.OrderJpa;

public interface OrderDatabaseConnection<T> {

    T getById(Long id);

    T save(OrderJpa orderJpa);
}
