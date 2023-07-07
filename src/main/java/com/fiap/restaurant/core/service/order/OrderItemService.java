package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.adapter.driven.data.repository.order.OrderItemRepository;
import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.model.order.OrderItem;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem save(OrderItem orderItem) {
        return this.orderItemRepository.save(orderItem);
    }
}
