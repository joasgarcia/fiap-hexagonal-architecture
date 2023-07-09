package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.adapter.driven.data.repository.order.OrderItemRepository;
import com.fiap.restaurant.core.model.order.OrderItem;
import com.fiap.restaurant.core.repository.order.IOrderItemRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrderItemService {

    private final IOrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem save(OrderItem orderItem) {
        return this.orderItemRepository.save(orderItem);
    }

    public void delete(Long orderId, Long itemId) {
        this.orderItemRepository.delete(orderId, itemId);
    }
}
