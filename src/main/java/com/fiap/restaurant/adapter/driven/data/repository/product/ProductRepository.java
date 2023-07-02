package com.fiap.restaurant.adapter.driven.data.repository.product;

import com.fiap.restaurant.adapter.driven.data.entity.product.ProductEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.product.ProductMapper;
import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.repository.product.IProductRepository;
import org.springframework.beans.BeanUtils;
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
        ProductEntity productEntity = ProductMapper.INSTANCE.toProductEntity(product);
        productEntity = this.productJpaRepository.save(productEntity);
        return ProductMapper.INSTANCE.toProduct(productEntity);
    }

    @Override
    public Product update(Long id, Product product) {
        ProductEntity productEntity = this.productJpaRepository.getReferenceById(id);
        BeanUtils.copyProperties(product, productEntity, "id");
        productEntity = this.productJpaRepository.save(productEntity);
        return ProductMapper.INSTANCE.toProduct(productEntity);
    }

    @Override
    public void delete(Long id) {
        ProductEntity productEntity = this.productJpaRepository.getReferenceById(id);
        this.productJpaRepository.delete(productEntity);
    }

    @Override
    public List<Product> list() {
        List<ProductEntity> list = this.productJpaRepository.findAll();
        return ProductMapper.INSTANCE.toProductList(list);
    }

    @Override
    public boolean existsById(Long id) {
        return this.productJpaRepository.existsById(id);
    }
}
