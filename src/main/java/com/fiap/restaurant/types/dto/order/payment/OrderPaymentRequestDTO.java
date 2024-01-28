package com.fiap.restaurant.types.dto.order.payment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class OrderPaymentRequestDTO {

    private Long customerId;
    private Double value;

    public OrderPaymentRequestDTO(Long customerId, Double value) {
        this.customerId = customerId;
        this.value = value;
    }
}
