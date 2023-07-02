package com.fiap.restaurant.adapter.driven.data.mapper.order;


import com.fiap.restaurant.adapter.driven.data.entity.order.ItemEntity;
import com.fiap.restaurant.core.model.order.Item;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toItem(ItemEntity itemEntity);
    ItemEntity toItemEntity(Item item);

}
