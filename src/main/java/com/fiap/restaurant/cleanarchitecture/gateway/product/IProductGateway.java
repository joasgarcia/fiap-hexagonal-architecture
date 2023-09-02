package com.fiap.restaurant.cleanarchitecture.gateway.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;

import java.util.List;

public interface IProductGateway {

    void save(Product product);
    void update(Long id, Product product);
    List<Product> list();
    List<Product> findAllByCategory(String category);
    Product getById(Long id);
    boolean existsById(Long id);
    void delete(Long id);
}
