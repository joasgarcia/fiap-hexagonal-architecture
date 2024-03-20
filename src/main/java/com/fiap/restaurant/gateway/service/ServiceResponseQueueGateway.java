package com.fiap.restaurant.gateway.service;

import com.fiap.restaurant.ApplicationConfig;
import com.fiap.restaurant.controller.order.OrderController;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.gateway.order.IOrderProductionGateway;
import com.fiap.restaurant.gateway.order.OrderProductionGateway;
import com.fiap.restaurant.types.dto.service.ServiceResponseQueueDTO;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.usecase.ServiceResponseUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceResponseQueueGateway {

    private final OrderDatabaseConnection orderDatabaseConnection;

    private final CustomerDatabaseConnection customerDatabaseConnection;

    private final IOrderProductionGateway orderProductionGateway;

    public ServiceResponseQueueGateway(ApplicationConfig applicationConfig, OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection) {
        this.orderDatabaseConnection = orderDatabaseConnection;
        this.customerDatabaseConnection = customerDatabaseConnection;
        this.orderProductionGateway = new OrderProductionGateway(applicationConfig);
    }

    @SqsListener(value = "response-q")
    @Transactional
    public void receiveMessage(ServiceResponseQueueDTO message) {
        switch (message.type()) {
            case ORDER_PAYMENT_FINISHED:
                Long orderId = Long.valueOf(message.data().get("id"));

                System.out.println("Pagamento do pedido " + orderId + " aprovado");
                ServiceResponseUseCase.handlePaymentFinished(orderId, orderDatabaseConnection, customerDatabaseConnection, orderProductionGateway);
                break;

            case ORDER_PRODUCTION_FINISHED:
                Long orderIdProduction = Long.valueOf(message.data().get("id"));

                System.out.println("Produção do pedido " + orderIdProduction + " finalizada");
                ServiceResponseUseCase.handleProductionFinished(orderIdProduction, orderDatabaseConnection, customerDatabaseConnection);
                break;

            default:
                throw new NotImplementedException("ServiceResponseQueueType não implementado: " + message.type());
        }

    }
}
