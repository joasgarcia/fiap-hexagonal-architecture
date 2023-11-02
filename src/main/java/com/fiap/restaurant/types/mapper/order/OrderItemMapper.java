package com.fiap.restaurant.types.mapper.order;

import com.fiap.restaurant.entity.order.OrderItem;
import com.fiap.restaurant.external.db.order.OrderItemJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemJpa toOrderItemJpa(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemJpa orderItemJpa);
}
