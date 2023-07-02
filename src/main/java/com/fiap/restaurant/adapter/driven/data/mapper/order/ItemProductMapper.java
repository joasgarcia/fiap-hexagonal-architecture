package com.fiap.restaurant.adapter.driven.data.mapper.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.ItemProductEntity;
import com.fiap.restaurant.core.model.order.ItemProduct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemProductMapper {

    ItemProductMapper INSTANCE = Mappers.getMapper(ItemProductMapper.class);

    ItemProduct toItemProduct(ItemProductEntity itemProductEntity);
    ItemProductEntity toItemProductEntity(ItemProduct itemProduct);
}
