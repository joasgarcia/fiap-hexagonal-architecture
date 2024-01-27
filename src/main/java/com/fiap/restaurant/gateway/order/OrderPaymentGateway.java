package com.fiap.restaurant.gateway.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurant.ApplicationConfig;
import com.fiap.restaurant.types.dto.order.payment.OrderPaymentRequestDTO;
import com.fiap.restaurant.types.dto.order.payment.OrderPaymentResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OrderPaymentGateway implements IOrderPaymentGateway {

    private final ApplicationConfig applicationConfig;

    public OrderPaymentGateway(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public OrderPaymentResponseDTO registerOrder(Long customerId, Double value) {
        try {
            OrderPaymentRequestDTO orderPaymentRequestDTO = new OrderPaymentRequestDTO(customerId, value);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(orderPaymentRequestDTO);
            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

            ResponseEntity<OrderPaymentResponseDTO> orderPaymentRestResponseDTO = new RestTemplate().postForEntity(applicationConfig.getPaymentUrl() + "/payment", entity, OrderPaymentResponseDTO.class);

            return orderPaymentRestResponseDTO.getBody();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao realizar comunicação com o serviço de pagamentos");
        }
    }
}
