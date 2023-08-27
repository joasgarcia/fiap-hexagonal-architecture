package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product;

public interface ProductDatabaseConnection<T> {

    void save(T product);

}
