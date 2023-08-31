package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order;

public interface ItemProductDatabaseConnection<T> {

    T save(T itemProduct);
    void delete(Long id);
}
