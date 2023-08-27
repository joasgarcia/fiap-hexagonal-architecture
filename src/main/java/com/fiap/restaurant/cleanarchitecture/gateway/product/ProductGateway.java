package com.fiap.restaurant.cleanarchitecture.gateway.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.external.db.product.ProductJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;

@SuppressWarnings("unchecked")
public class ProductGateway implements IProductGateway {

    private final ProductDatabaseConnection productDatabaseConnection;

    public ProductGateway(ProductDatabaseConnection productDatabaseConnection) {
        this.productDatabaseConnection = productDatabaseConnection;
    }

    @Override
    public void save(Product product) {
        ProductJpa productJpa = new ProductJpa();
        productJpa.setName(product.getName());
        productJpa.setDescription(product.getDescription());
        productJpa.setPrice(product.getPrice());
        productJpa.setCategory(product.getCategory());

        this.productDatabaseConnection.save(productJpa);
    }
}
