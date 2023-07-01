package com.fiap.restaurant.core.repository;

import com.fiap.restaurant.core.model.Product;

import java.util.List;

public interface IProductRepository {

    Product save(Product product);
    Product update(Long id, Product product);
    void delete(Long id);
    List<Product> list();
    boolean existsById(Long id);
}
