package com.fiap.restaurant.core.mapper.product;

import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.model.product.ProductFacade;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductFacade productFacade);
    ProductFacade toProductFacade(Product product);

}
