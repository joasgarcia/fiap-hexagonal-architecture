package com.fiap.restaurant.types.dto.order.payment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class OrderPaymentPayloadMessageBrokerDTO {

    private Long orderId;
    private Double value;

    public OrderPaymentPayloadMessageBrokerDTO(Long orderId, Double value) {
        this.orderId = orderId;
        this.value = value;
    }
}
