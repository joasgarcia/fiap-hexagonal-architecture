package com.fiap.restaurant.cleanarchitecture.gateway.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.external.db.product.ProductJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.product.ProductMapper;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Product> list() {
        List<ProductJpa> productJpaList = this.productDatabaseConnection.list();
        List<Product> productList = new ArrayList<>();

        for (ProductJpa productJpa : productJpaList) {
            productList.add(ProductMapper.INSTANCE.toProduct(productJpa));
        }

        return productList;
    }

    @Override
    public List<Product> findAllByCategory(String category) {
        List<ProductJpa> productJpaList = this.productDatabaseConnection.findAllByCategory(category);
        List<Product> productList = new ArrayList<>();

        for (ProductJpa productJpa : productJpaList) {
            productList.add(ProductMapper.INSTANCE.toProduct(productJpa));
        }

        return productList;
    }

    @Override
    public Product getById(Long id) {
        ProductJpa productJpa = (ProductJpa) this.productDatabaseConnection.getById(id);
        if (productJpa == null) return null;

        return ProductMapper.INSTANCE.toProduct(productJpa);
    }

    @Override
    public boolean existsById(Long id) {
        return this.productDatabaseConnection.existsById(id);
    }

    @Override
    public void delete(Long id) {
        ProductJpa productJpa = (ProductJpa) this.productDatabaseConnection.getById(id);
        this.productDatabaseConnection.delete(productJpa);
    }
}
