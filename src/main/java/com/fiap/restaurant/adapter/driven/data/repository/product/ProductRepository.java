package com.fiap.restaurant.adapter.driven.data.repository.product;

import com.fiap.restaurant.adapter.driven.data.entity.product.ProductEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.ProductMapper;
import com.fiap.restaurant.core.model.Product;
import com.fiap.restaurant.core.repository.IProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepository implements IProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepository(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductMapper.toProductEntity(product);
        productEntity = this.productJpaRepository.save(productEntity);
        return ProductMapper.toProduct(productEntity);
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public void delete(Long id) {
        ProductEntity productEntity = this.productJpaRepository.getReferenceById(id);
        this.productJpaRepository.delete(productEntity);
    }

    @Override
    public List<Product> list() {
        List<ProductEntity> list = this.productJpaRepository.findAll();
        return ProductMapper.toProductList(list);
    }
}
