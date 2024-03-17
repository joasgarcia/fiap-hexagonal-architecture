package com.fiap.restaurant.controller.order;

import com.fiap.restaurant.ApplicationConfig;
import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.messagebroker.MessageBroker;
import com.fiap.restaurant.gateway.customer.CustomerGateway;
import com.fiap.restaurant.gateway.customer.ICustomerGateway;
import com.fiap.restaurant.gateway.order.*;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderItemDatabaseConnection;
import com.fiap.restaurant.usecase.order.OrderUseCase;

public class OrderController {

    public static Order findById(Long id, OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection) {
        OrderGateway orderGateway = new OrderGateway(orderDatabaseConnection, customerDatabaseConnection);
        return OrderUseCase.findById(id, orderGateway);
    }

    public static Order updatePaymentStatus(Long id, String paymentStatus, OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection) {
        OrderGateway orderGateway = new OrderGateway(orderDatabaseConnection, customerDatabaseConnection);
        OrderPaymentStatus paymentStatusParsed = OrderPaymentStatus.valueOf(paymentStatus);
        return OrderUseCase.updatePaymentStatus(id, paymentStatusParsed, orderGateway);
    }

    public static Order updateStatus(Long id, String status, OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection) {
        OrderGateway orderGateway = new OrderGateway(orderDatabaseConnection, customerDatabaseConnection);
        OrderStatus statusParsed = OrderStatus.valueOf(status);
        return OrderUseCase.updateStatus(id, statusParsed, orderGateway);
    }

    public static Order save(SaveOrderDTO saveOrderDTO, OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection, ItemDatabaseConnection itemDatabaseConnection, OrderItemDatabaseConnection orderItemDatabaseConnection, ApplicationConfig applicationConfig, MessageBroker messageBroker) {
        IOrderGateway orderGateway = new OrderGateway(orderDatabaseConnection, customerDatabaseConnection);
        ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
        IItemGateway itemGateway = new ItemGateway(itemDatabaseConnection);
        IOrderItemGateway orderItemGateway = new OrderItemGateway(orderItemDatabaseConnection, itemDatabaseConnection, orderDatabaseConnection);
        IOrderPaymentGateway orderPaymentGateway = new OrderPaymentGateway(messageBroker);
        IOrderProductionGateway orderProductionGateway = new OrderProductionGateway(applicationConfig);

        return OrderUseCase.save(saveOrderDTO, orderGateway, customerGateway, itemGateway, orderItemGateway, orderPaymentGateway, orderProductionGateway);
    }
}
