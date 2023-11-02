package com.fiap.restaurant.types.mapper.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.external.db.order.OrderJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderItemMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderJpa orderJpa);

    default List<Order> toOrderList(List<OrderJpa> orderJpaList) {
        List<Order> orderList = new ArrayList<>();
        orderJpaList.forEach(orderJpa -> orderList.add(toOrder(orderJpa)));
        return orderList;
    }
}
