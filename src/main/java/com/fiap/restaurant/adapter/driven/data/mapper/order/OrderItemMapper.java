package com.fiap.restaurant.adapter.driven.data.mapper.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.OrderItemEntity;
import com.fiap.restaurant.core.model.order.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemEntity toOrderItemEntity(OrderItem orderItem);

    OrderItem toOrderItem(OrderItemEntity orderItemEntity);
}
