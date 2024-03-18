package com.fiap.restaurant.types.dto.order.payment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record OrderPaymentPayloadMessageBrokerDTO(Long orderId, Double value) {

}
