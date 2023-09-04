package com.fiap.restaurant.external.db.product;

import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductJpaConnection implements ProductDatabaseConnection<ProductJpa> {

    private final CleanProductJpaRepository productJpaRepository;

    public ProductJpaConnection(CleanProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public ProductJpa save(ProductJpa product) {
        return this.productJpaRepository.save(product);
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
    public Optional<ProductJpa> getById(Long id) {
        return this.productJpaRepository.findById(id);
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
