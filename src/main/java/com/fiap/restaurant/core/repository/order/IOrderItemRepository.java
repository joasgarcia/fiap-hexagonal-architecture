package com.fiap.restaurant.core.repository.order;

import com.fiap.restaurant.core.model.order.OrderItem;

public interface IOrderItemRepository {

    OrderItem save(OrderItem orderItem);
}
