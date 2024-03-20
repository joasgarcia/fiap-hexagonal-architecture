package com.fiap.restaurant.gateway.service;

import com.fiap.restaurant.external.messagebroker.MessageBroker;
import com.fiap.restaurant.external.messagebroker.SqsMessageBroker;
import com.fiap.restaurant.gateway.order.OrderProductionGateway;
import com.fiap.restaurant.types.dto.service.ServiceResponseQueueDTO;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.usecase.ServiceResponseUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceResponseQueueGateway {

    private final OrderDatabaseConnection orderDatabaseConnection;

    private final CustomerDatabaseConnection customerDatabaseConnection;

    public ServiceResponseQueueGateway(OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection) {
        this.orderDatabaseConnection = orderDatabaseConnection;
        this.customerDatabaseConnection = customerDatabaseConnection;
    }

    @SqsListener(value = "response-q")
    @Transactional
        public void receiveMessage(ServiceResponseQueueDTO message) {
        switch (message.type()) {
            case ORDER_PAYMENT_FAILED:
                Long orderIdFailed = Long.valueOf(message.data().get("id"));

                System.out.println("Pagamento do pedido " + orderIdFailed + " falhou");

                ServiceResponseUseCase.handlePaymentFailed(orderIdFailed, orderDatabaseConnection, customerDatabaseConnection);
                break;

            case ORDER_PAYMENT_FINISHED:
                Long orderId = Long.valueOf(message.data().get("id"));

                System.out.println("Pagamento do pedido " + orderId + " aprovado");

                final MessageBroker messageBroker = new SqsMessageBroker("production-q");
                final OrderProductionGateway orderProductionGateway = new OrderProductionGateway(messageBroker);

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
