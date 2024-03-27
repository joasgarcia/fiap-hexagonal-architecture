package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.Order;

public interface IOrderPaymentGateway {

    Boolean registerOrder(Long orderId, Double value);

    Boolean refund(Order order);
}
