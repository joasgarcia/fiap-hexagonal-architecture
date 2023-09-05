package com.fiap.restaurant.cleanarchitecture.gateway.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.OrderItem;

public interface IOrderItemGateway {

    OrderItem save(OrderItem orderItem);
}
