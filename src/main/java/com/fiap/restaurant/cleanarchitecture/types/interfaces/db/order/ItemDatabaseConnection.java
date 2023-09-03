package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order;

import java.util.Optional;

public interface ItemDatabaseConnection<T> {

    T save(T itemProduct);
    Optional<T> getById(Long id);
    void delete(Long id);
    boolean existsById(Long id);
}
