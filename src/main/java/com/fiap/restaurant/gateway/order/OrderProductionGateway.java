package com.fiap.restaurant.gateway.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurant.ApplicationConfig;
import com.fiap.restaurant.types.dto.order.production.OrderProductionRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OrderProductionGateway implements IOrderProductionGateway {

    private final ApplicationConfig applicationConfig;

    public OrderProductionGateway(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public Boolean registerOrder(Long orderId) {
        try {
            OrderProductionRequestDTO orderProductionRequestDTO = new OrderProductionRequestDTO(orderId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(orderProductionRequestDTO);
            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

            ResponseEntity<Boolean> orderProductionQueued = new RestTemplate().postForEntity(applicationConfig.getProductionUrl() + "/queue/", entity, Boolean.class);

            return orderProductionQueued.getBody();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao realizar comunicação com o serviço de produção de pedidos");
        }
    }
}
