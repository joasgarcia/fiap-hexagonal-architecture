package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order;

import com.fiap.restaurant.cleanarchitecture.external.db.order.OrderJpa;

import java.util.List;

public interface OrderDatabaseConnection<T> {

    T getById(Long id);

    T save(OrderJpa orderJpa);

    List<OrderJpa> listOrderedByStatusAndId();
}
