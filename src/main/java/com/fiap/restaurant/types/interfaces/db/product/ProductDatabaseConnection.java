package com.fiap.restaurant.types.interfaces.db.product;

import java.util.List;
import java.util.Optional;

public interface ProductDatabaseConnection<T> {

    T save(T product);
    List<T> list();
    List<T> findAllByCategory(String category);
    Optional<T> getById(Long id);
    boolean existsById(Long id);
    void delete(T product);
}
