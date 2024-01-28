package com.fiap.restaurant.types.dto.order.production;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class OrderProductionRequestDTO {

    private Long orderId;

    public OrderProductionRequestDTO(Long orderId) {
        this.orderId = orderId;
    }
}
