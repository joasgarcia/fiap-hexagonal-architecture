package com.fiap.restaurant.adapter.driven.data.mapper.order;


import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.adapter.driven.data.entity.order.OrderItemEntity;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.model.order.OrderItem;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface EntityToOrderMapper {

    EntityToOrderMapper INSTANCE = Mappers.getMapper(EntityToOrderMapper.class);

    Order toOrder(OrderEntity orderEntity);
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemEntity s);

    default List<Order> toOrderList(List<OrderEntity> orderEntityList) {
        List<Order> orderList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderList.add(toOrder(orderEntity)));
        return orderList;
    }

}
