package com.fiap.restaurant.cleanarchitecture.external.db.product;

import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import org.springframework.stereotype.Component;

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
}
