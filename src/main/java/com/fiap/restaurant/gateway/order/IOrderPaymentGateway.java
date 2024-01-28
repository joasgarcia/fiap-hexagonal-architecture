package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.types.dto.order.payment.OrderPaymentResponseDTO;

public interface IOrderPaymentGateway {

    OrderPaymentResponseDTO registerOrder(Long customerId, Double value);

}
