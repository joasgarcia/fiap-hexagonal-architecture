package com.fiap.restaurant.core.repository.order;

import com.fiap.restaurant.core.model.order.OrderItem;

public interface IOrderItemRepository {

    void save(OrderItem orderItem);
    void delete(Long orderId, Long itemId);
}
