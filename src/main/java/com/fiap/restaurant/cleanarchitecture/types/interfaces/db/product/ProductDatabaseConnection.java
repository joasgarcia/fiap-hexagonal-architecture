package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product;

import java.util.List;

public interface ProductDatabaseConnection<T> {

    void save(T product);
    List<T> list();
    List<T> findAllByCategory(String category);
    T getById(Long id);
    boolean existsById(Long id);
    void delete(T product);
}
