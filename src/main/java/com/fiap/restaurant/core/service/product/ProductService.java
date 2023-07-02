package com.fiap.restaurant.core.service.product;

import com.fiap.restaurant.core.exception.ResourceNotFoundException;
import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.repository.product.IProductRepository;

import java.util.List;

public class ProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        if (!this.productRepository.existsById(id)) throw new ResourceNotFoundException("Produto não encontrado");

        return this.productRepository.update(id, product);
    }

    public void delete(Long id) {
        this.productRepository.delete(id);
    }

    public List<Product> list() {
        return this.productRepository.list();
    }

}
