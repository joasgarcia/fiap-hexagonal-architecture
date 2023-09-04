package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.Order;

public interface IOrderGateway {

    Order getById(Long id);

    Order update(Order order);

}
