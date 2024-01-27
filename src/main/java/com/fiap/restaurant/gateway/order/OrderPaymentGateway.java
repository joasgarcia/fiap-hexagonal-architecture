package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.types.dto.order.OrderPaymentRestResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class OrderPaymentGateway implements IOrderPaymentGateway {

    @Value("fiap.payment.url")
    private String paymentUrl;

    @Override
    public OrderPaymentRestResponseDTO registerOrder(Long id) {
        OrderPaymentRestResponseDTO orderPaymentRestResponseDTO = new RestTemplate().getForObject(paymentUrl + "/payment", OrderPaymentRestResponseDTO.class);
        System.out.println(orderPaymentRestResponseDTO.getSuccess());
        System.out.println(orderPaymentRestResponseDTO.getMessage());

        return orderPaymentRestResponseDTO;
    }
}
