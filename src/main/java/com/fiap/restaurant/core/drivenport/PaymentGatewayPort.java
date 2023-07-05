package com.fiap.restaurant.core.drivenport;

import java.math.BigDecimal;

public interface PaymentGatewayPort {

    public Boolean makePayment(Long orderId);
}
