package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.types.dto.order.OrderPaymentRestResponseDTO;

public interface IOrderPaymentGateway {

    OrderPaymentRestResponseDTO registerOrder(Long id);

}
