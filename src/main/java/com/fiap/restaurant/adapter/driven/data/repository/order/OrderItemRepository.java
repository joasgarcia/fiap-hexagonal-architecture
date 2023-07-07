package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.OrderItemEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.order.OrderItemMapper;
import com.fiap.restaurant.core.model.order.OrderItem;
import com.fiap.restaurant.core.repository.order.IOrderItemRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderItemRepository implements IOrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderItemRepository(OrderItemJpaRepository orderItemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = this.orderItemJpaRepository.save(OrderItemMapper.INSTANCE.toOrderItemEntity(orderItem));
        return OrderItemMapper.INSTANCE.toOrderItem(orderItemEntity);
    }

    @Override
    public void delete(Long orderId, Long itemId) {
        this.orderItemJpaRepository.deleteByOrderIdAndItemId(orderId, itemId);
    }
}
