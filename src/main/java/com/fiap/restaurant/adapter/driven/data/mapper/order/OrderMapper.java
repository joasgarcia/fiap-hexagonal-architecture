package com.fiap.restaurant.adapter.driven.data.mapper.order;


import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.adapter.driven.data.entity.order.OrderItemEntity;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.model.order.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderEntity orderEntity);
    OrderEntity toOrderEntity(Order order);
    Order toOrder(Optional<OrderEntity> orderEntity);

    default List<Order> toOrderList(List<OrderEntity> orderEntityList) {
        List<Order> orderList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderList.add(toOrder(orderEntity)));
        return orderList;
    }

    OrderItemEntity map(OrderItem orderItem);
}
