package com.fiap.restaurant.cleanarchitecture.usecase.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.Order;
import com.fiap.restaurant.cleanarchitecture.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.cleanarchitecture.gateway.order.IOrderGateway;
import com.fiap.restaurant.cleanarchitecture.types.exception.ResourceNotFoundException;

public class OrderUseCase {

    public static Order findById(Long id, IOrderGateway orderGateway) {
        Order order = orderGateway.getById(id);

        if (order == null) throw new ResourceNotFoundException("Pedido não encontrado");

        return order;
    }

    public static Order updatePaymentStatus(Long id, OrderPaymentStatus paymentStatus, IOrderGateway orderGateway) {
        Order order = orderGateway.getById(id);

        order.setPaymentStatus(paymentStatus);

        orderGateway.update(order);

        return order;
    }
}
