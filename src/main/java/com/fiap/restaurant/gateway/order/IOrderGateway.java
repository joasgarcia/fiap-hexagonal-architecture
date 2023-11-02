package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.Order;

import java.util.List;

public interface IOrderGateway {

    Order getById(Long id);

    Order update(Order order);
    Order save(Order order);

    List<Order> listOrderedByStatusAndId();
}
