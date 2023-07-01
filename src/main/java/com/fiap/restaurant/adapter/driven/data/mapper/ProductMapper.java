package com.fiap.restaurant.adapter.driven.data.mapper;

import com.fiap.restaurant.adapter.driven.data.entity.product.ProductEntity;
import com.fiap.restaurant.core.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductEntity productEntity);

    ProductEntity toProductEntity(Product product);

    default List<Product> toProductList(List<ProductEntity> productEntityList) {
        List<Product> productList = new ArrayList<>();
        productEntityList.forEach(productEntity -> productList.add(toProduct(productEntity)));
        return productList;
    }

}
