package com.fiap.restaurant.adapter.driven.data.mapper.order;


import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.core.model.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderEntity orderEntity);

    default List<Order> toOrderList(List<OrderEntity> orderEntityList) {
        List<Order> orderList = new ArrayList<>();
        orderEntityList.forEach(orderEntity -> orderList.add(toOrder(orderEntity)));
        return orderList;
    }
}
