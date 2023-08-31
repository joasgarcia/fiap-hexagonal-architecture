package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order;

public interface ItemDatabaseConnection<T> {

    T save(T itemProduct);
    T getById(Long id);
    void delete(Long id);
}
