package com.fiap.restaurant.cleanarchitecture.types.mapper.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.ItemProduct;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemProductJpa;
import com.fiap.restaurant.cleanarchitecture.types.mapper.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemProductMapper {

    ItemProductMapper INSTANCE = Mappers.getMapper(ItemProductMapper.class);

    default ItemProduct toItemProduct(ItemProductJpa itemProductJpa) {
        ItemProduct itemProduct = new ItemProduct();
        itemProduct.setId((itemProductJpa.getId()));
        itemProduct.setProduct(ProductMapper.INSTANCE.toProduct(itemProductJpa.getProduct()));
        itemProduct.setItem(ItemMapper.INSTANCE.toItem(itemProductJpa.getItem()));

        return itemProduct;
    }
}
