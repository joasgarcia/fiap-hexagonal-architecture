package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order;

import java.util.List;

public interface ItemProductDatabaseConnection<T> {

    T save(T itemProduct);
    void delete(Long id);
    List<T> findAllByItemId(Long itemId);
}
