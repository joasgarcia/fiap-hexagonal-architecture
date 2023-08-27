package com.fiap.restaurant.cleanarchitecture.gateway.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;

import java.util.List;

public interface IProductGateway {

    void save(Product product);

    List<Product> list();

    List<Product> findAllByCategory(String category);
}
