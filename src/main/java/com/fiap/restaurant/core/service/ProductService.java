package com.fiap.restaurant.core.service;

import com.fiap.restaurant.core.model.Product;
import com.fiap.restaurant.core.repository.IProductRepository;

import java.util.List;

public class ProductService {

    private final IProductRepository itemRepository;

    public ProductService(IProductRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Product save(Product product) {
        return this.itemRepository.save(product);
    }

    public Product update(Product product) {
        return this.itemRepository.update(product);
    }

    public void delete(Long id) {
        this.itemRepository.delete(id);
    }

    public List<Product> list() {
        return this.itemRepository.list();
    }

}
