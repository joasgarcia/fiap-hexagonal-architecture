package com.fiap.restaurant.gateway.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.external.messagebroker.MessageBroker;
import com.fiap.restaurant.external.messagebroker.SqsMessageBroker;
import com.fiap.restaurant.types.dto.order.payment.OrderPaymentPayloadMessageBrokerDTO;

public class OrderPaymentGateway implements IOrderPaymentGateway {

    @Override
    public Boolean registerOrder(Long orderId, Double value) {
        try {
            OrderPaymentPayloadMessageBrokerDTO orderPaymentPayloadMessageBrokerDTO = new OrderPaymentPayloadMessageBrokerDTO(orderId, value);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(orderPaymentPayloadMessageBrokerDTO);

            MessageBroker messageBroker = new SqsMessageBroker("payment-q");
            messageBroker.send(requestJson);

            return true;
        } catch (Exception exception) {
            throw new RuntimeException("Ocorreu um erro desconhecido", exception);
        }
    }

    @Override
    public Boolean refund(Order order) {
        try {
            OrderPaymentPayloadMessageBrokerDTO orderPaymentPayloadMessageBrokerDTO = new OrderPaymentPayloadMessageBrokerDTO(order.getId(), order.getTotalValue());

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(orderPaymentPayloadMessageBrokerDTO);

            MessageBroker messageBroker = new SqsMessageBroker("payment-refund-q");
            messageBroker.send(requestJson);

            return true;
        } catch (Exception exception) {
            throw new RuntimeException("Ocorreu um erro desconhecido", exception);
        }
    }
}
