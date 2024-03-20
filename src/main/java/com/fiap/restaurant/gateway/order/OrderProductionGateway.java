package com.fiap.restaurant.gateway.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurant.controller.order.OrderController;
import com.fiap.restaurant.external.messagebroker.MessageBroker;
import com.fiap.restaurant.external.messagebroker.SqsMessageBroker;
import com.fiap.restaurant.types.dto.order.production.OrderProductionRequestDTO;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.usecase.order.OrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class OrderProductionGateway implements IOrderProductionGateway {

    private final OrderDatabaseConnection orderDatabaseConnection;

    private final CustomerDatabaseConnection customerDatabaseConnection;

    public OrderProductionGateway(OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection) {
        this.orderDatabaseConnection = orderDatabaseConnection;
        this.customerDatabaseConnection = customerDatabaseConnection;
    }

    @Override
    public Boolean registerOrder(Long orderId) {
        try {
            OrderProductionRequestDTO orderProductionRequestDTO = new OrderProductionRequestDTO(orderId);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(orderProductionRequestDTO);

            MessageBroker messageBroker = new SqsMessageBroker("production-q");
            messageBroker.send(requestJson);

            return true;
        } catch (Exception exception) {
            OrderController.refund(orderId, orderDatabaseConnection, customerDatabaseConnection);
            return false;
        }
    }
}
