package com.fiap.restaurant.cleanarchitecture.external.db.product;

import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductJpaConnection implements ProductDatabaseConnection<ProductJpa> {

    private final CleanProductJpaRepository productJpaRepository;

    public ProductJpaConnection(CleanProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public void save(ProductJpa product) {
        this.productJpaRepository.save(product);
    }

    @Override
    public List<ProductJpa> list() {
        return this.productJpaRepository.findAll();
    }

    @Override
    public List<ProductJpa> findAllByCategory(String category) {
        return this.productJpaRepository.findAllByCategory(category);
    }

    @Override
    public ProductJpa getById(Long id) {
        return this.productJpaRepository.getReferenceById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.productJpaRepository.existsById(id);
    }

    @Override
    public void delete(ProductJpa product) {
        this.productJpaRepository.delete(product);
    }
}
