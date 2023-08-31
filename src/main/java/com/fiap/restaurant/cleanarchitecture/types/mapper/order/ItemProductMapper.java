package com.fiap.restaurant.cleanarchitecture.types.mapper.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.Item;
import com.fiap.restaurant.cleanarchitecture.entity.order.ItemProduct;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemProductJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemProductMapper {

    ItemProductMapper INSTANCE = Mappers.getMapper(ItemProductMapper.class);

    ItemProduct toItemProduct(ItemProductJpa itemProductJpa);
}
