package com.fiap.restaurant.cleanarchitecture.gateway.product;

import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.product.ProductMapper;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class ProductGateway implements IProductGateway {

    private final ProductDatabaseConnection productDatabaseConnection;

    public ProductGateway(ProductDatabaseConnection productDatabaseConnection) {
        this.productDatabaseConnection = productDatabaseConnection;
    }

    @Override
    public Product save(Product product) {
        ProductJpa productJpa = new ProductJpa();
        productJpa.setName(product.getName());
        productJpa.setDescription(product.getDescription());
        productJpa.setPrice(product.getPrice());
        productJpa.setCategory(product.getCategory());

        productJpa = (ProductJpa) this.productDatabaseConnection.save(productJpa);

        return ProductMapper.INSTANCE.toProduct(productJpa);
    }

    @Override
    public Product update(Long id, Product product) {
        Optional<ProductJpa> productJpa = this.productDatabaseConnection.getById(id);

        BeanUtils.copyProperties(product, productJpa.get(), "id");
        ProductJpa updatedProductJpa = (ProductJpa) this.productDatabaseConnection.save(productJpa.get());

        return ProductMapper.INSTANCE.toProduct(updatedProductJpa);
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
        Optional<ProductJpa> productJpa = this.productDatabaseConnection.getById(id);
        return productJpa.map(ProductMapper.INSTANCE::toProduct).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return this.productDatabaseConnection.existsById(id);
    }

    @Override
    public void delete(Long id) {
        Optional<ProductJpa> productJpa = this.productDatabaseConnection.getById(id);
        productJpa.ifPresent(this.productDatabaseConnection::delete);
    }
}
