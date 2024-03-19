package com.fiap.restaurant.usecase;

import com.fiap.restaurant.controller.order.OrderController;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.gateway.order.IOrderProductionGateway;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;

public class ServiceResponseUseCase {

    public static void handlePaymentFinished(Long orderId, OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection, IOrderProductionGateway orderProductionGateway) {
        OrderController.updatePaymentStatus(orderId, OrderPaymentStatus.APPROVED, orderDatabaseConnection, customerDatabaseConnection);
        OrderController.updateStatus(orderId, OrderStatus.PREPARING, orderDatabaseConnection, customerDatabaseConnection);
        orderProductionGateway.registerOrder(orderId);
    }
}
