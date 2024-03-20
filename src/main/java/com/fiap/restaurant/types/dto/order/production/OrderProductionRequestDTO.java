package com.fiap.restaurant.types.dto.order.production;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record OrderProductionRequestDTO(Long orderId) {}
