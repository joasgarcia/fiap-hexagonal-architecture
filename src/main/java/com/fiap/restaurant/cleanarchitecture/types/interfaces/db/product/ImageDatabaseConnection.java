package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product;

import java.util.List;
import java.util.Optional;

public interface ImageDatabaseConnection<T> {

    T save(T image);
    void delete(T image);
    Optional<T> getById(Long id);
    boolean existsById(Long id);
    List<T> findAllByProductId(Long productId);

}
