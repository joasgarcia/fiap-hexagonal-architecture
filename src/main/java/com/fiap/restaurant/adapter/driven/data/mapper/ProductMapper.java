package com.fiap.restaurant.adapter.driven.data.mapper;

import com.fiap.restaurant.adapter.driven.data.entity.ProductEntity;
import com.fiap.restaurant.core.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static Product toProduct(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setCategory(productEntity.getCategory());
        product.setPrice(productEntity.getPrice());

        return product;
    }

    public static ProductEntity toProductEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setCategory(product.getCategory());
        productEntity.setPrice(product.getPrice());

        return productEntity;
    }

    public static List<Product> toProductList(List<ProductEntity> productEntityList) {
        List<Product> productList = new ArrayList<>();

        for (ProductEntity productEntity : productEntityList) {
            productList.add(toProduct(productEntity));
        }

        return productList;
    }
}
