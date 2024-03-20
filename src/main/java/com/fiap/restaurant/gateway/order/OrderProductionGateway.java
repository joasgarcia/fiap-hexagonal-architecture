package com.fiap.restaurant.gateway.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurant.external.messagebroker.MessageBroker;
import com.fiap.restaurant.types.dto.order.production.OrderProductionRequestDTO;

public class OrderProductionGateway implements IOrderProductionGateway {

    private final MessageBroker messageBroker;

    public OrderProductionGateway(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public Boolean registerOrder(Long orderId) {
        try {
            OrderProductionRequestDTO orderProductionRequestDTO = new OrderProductionRequestDTO(orderId);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(orderProductionRequestDTO);

            this.messageBroker.send(requestJson);

            return true;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao realizar comunicação com o serviço de produção de pedidos");
        } catch (Exception exception) {
            throw new RuntimeException("Ocorreu um erro desconhecido", exception);
        }
    }
}
