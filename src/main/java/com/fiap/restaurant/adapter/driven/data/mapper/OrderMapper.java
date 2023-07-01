package com.fiap.restaurant.adapter.driven.data.mapper;


import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.core.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderEntity orderEntity);

    default List<Order> toOrderList(List<OrderEntity> orderEntityList) {
        List<Order> orderList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderList.add(toOrder(orderEntity)));
        return orderList;
    }
}
