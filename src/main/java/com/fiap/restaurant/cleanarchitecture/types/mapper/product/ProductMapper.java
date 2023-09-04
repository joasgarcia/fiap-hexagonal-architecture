package com.fiap.restaurant.cleanarchitecture.types.mapper.product;

import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.external.db.product.ProductJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductJpa productJpa);
}
