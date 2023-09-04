package com.fiap.restaurant.cleanarchitecture.controller.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.Order;
import com.fiap.restaurant.cleanarchitecture.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.cleanarchitecture.entity.order.OrderStatus;
import com.fiap.restaurant.cleanarchitecture.gateway.order.OrderGateway;
import com.fiap.restaurant.cleanarchitecture.presenter.order.OrderPresenter;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.OrderScreenPresenterDTO;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.usecase.order.OrderUseCase;

import java.util.List;

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

    public static List<OrderScreenPresenterDTO> listOrderedByStatus(OrderDatabaseConnection orderDatabaseConnection) {
        OrderGateway orderGateway = new OrderGateway(orderDatabaseConnection);
        List<Order> orderList = OrderUseCase.listOrderedByStatus(orderGateway);

        return OrderPresenter.buildOrderScreen(orderList);
    }
}
