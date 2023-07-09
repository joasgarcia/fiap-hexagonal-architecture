package com.fiap.restaurant.adapter.driven.data;

import com.fiap.restaurant.core.drivenport.PaymentGatewayPort;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoGatewayAdapter implements PaymentGatewayPort {
    @Override
    public Boolean makePayment(Long orderId) {
        return true;
    }
}
