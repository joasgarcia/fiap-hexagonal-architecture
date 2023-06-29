package com.fiap.restaurant.adapter.driven.data.mapper;

import com.fiap.restaurant.adapter.driven.data.entity.OrderEntity;
import com.fiap.restaurant.core.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static List<Order> toOrderList(List<OrderEntity> orderEntityList) {
        List<Order> orderList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            orderList.add(toOrder(orderEntity));
        }

        return orderList;
    }

    public static Order toOrder(OrderEntity orderEntity) {
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setCustomer(CustomerMapper.toCustomer(orderEntity.getCustomer()));
        order.setDateCreated(orderEntity.getDateCreated());

        return order;
    }

}
