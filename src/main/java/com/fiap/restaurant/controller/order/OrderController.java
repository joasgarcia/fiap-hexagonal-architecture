package com.fiap.restaurant.controller.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.gateway.order.OrderGateway;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.usecase.order.OrderUseCase;

public class OrderController {

    public static Order findById(Long id, OrderDatabaseConnection orderDatabaseConnection) {
        OrderGateway orderGateway = new OrderGateway(orderDatabaseConnection);
        return OrderUseCase.findById(id, orderGateway);
    }

    public static Order updatePaymentStatus(Long id, String paymentStatus, OrderDatabaseConnection orderDatabaseConnection) {
        OrderGateway orderGateway = new OrderGateway(orderDatabaseConnection);
        OrderPaymentStatus paymentStatusParsed = OrderPaymentStatus.valueOf(paymentStatus);
        return OrderUseCase.updatePaymentStatus(id, paymentStatusParsed, orderGateway);
    }

    public static Order updateStatus(Long id, String status, OrderDatabaseConnection orderDatabaseConnection) {
        OrderGateway orderGateway = new OrderGateway(orderDatabaseConnection);
        OrderStatus statusParsed = OrderStatus.valueOf(status);
        return OrderUseCase.updateStatus(id, statusParsed, orderGateway);
    }
}
