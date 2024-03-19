package com.fiap.restaurant.types.dto.service;

import java.util.Map;

public record ServiceResponseQueueDTO(
    ServiceResponseQueueType type,
    Map<String, String> data
) {
}
