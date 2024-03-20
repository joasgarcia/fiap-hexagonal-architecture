package com.fiap.restaurant.gateway.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurant.external.messagebroker.MessageBroker;
import com.fiap.restaurant.types.dto.order.payment.OrderPaymentPayloadMessageBrokerDTO;

public class OrderPaymentGateway implements IOrderPaymentGateway {


    private final MessageBroker messageBroker;

    public OrderPaymentGateway(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public Boolean registerOrder(Long orderId, Double value) {
        try {
            OrderPaymentPayloadMessageBrokerDTO orderPaymentPayloadMessageBrokerDTO = new OrderPaymentPayloadMessageBrokerDTO(orderId, value);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(orderPaymentPayloadMessageBrokerDTO);

            this.messageBroker.send(requestJson);
            return true;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao realizar comunicação com o serviço de pagamentos");
        } catch (Exception exception) {
            throw new RuntimeException("Ocorreu um erro desconhecido", exception);
        }
    }
}
