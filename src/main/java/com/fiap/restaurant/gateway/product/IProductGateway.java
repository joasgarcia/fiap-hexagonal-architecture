package com.fiap.restaurant.gateway.product;

import com.fiap.restaurant.entity.product.Product;

import java.util.List;

public interface IProductGateway {

    Product save(Product product);
    Product update(Long id, Product product);
    List<Product> list();
    List<Product> findAllByCategory(String category);
    Product getById(Long id);
    boolean existsById(Long id);
    void delete(Long id);
}
