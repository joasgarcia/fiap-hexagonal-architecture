package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order;

public interface OrderDatabaseConnection<T> {

    T getById(Long id);
}
