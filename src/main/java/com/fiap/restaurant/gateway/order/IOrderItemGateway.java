package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.OrderItem;

public interface IOrderItemGateway {

    OrderItem save(OrderItem orderItem);
}
