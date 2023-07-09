package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.ItemEntity;
import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.adapter.driven.data.entity.order.OrderItemEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.order.OrderItemMapper;
import com.fiap.restaurant.core.exception.ResourceNotFoundException;
import com.fiap.restaurant.core.model.order.OrderItem;
import com.fiap.restaurant.core.repository.order.IOrderItemRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderItemRepository implements IOrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final ItemJpaRepository itemJpaRepository;

    public OrderItemRepository(OrderItemJpaRepository orderItemJpaRepository, OrderJpaRepository orderJpaRepository, ItemJpaRepository itemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
        this.orderJpaRepository = orderJpaRepository;
        this.itemJpaRepository = itemJpaRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = OrderItemMapper.INSTANCE.toOrderItemEntity(orderItem);

        Long orderId = orderItem.getOrder().getId();
        Optional<OrderEntity> orderEntity = this.orderJpaRepository.findById(orderId);
        if (orderEntity.isEmpty()) throw new ResourceNotFoundException("Pedido [" + orderId + "] não encontrado");

        Long itemId = orderItem.getItem().getId();
        Optional<ItemEntity> itemEntity = this.itemJpaRepository.findById(itemId);
        if (itemEntity.isEmpty()) throw new ResourceNotFoundException("Item [" + itemId + "] não encontrado");

        orderItemEntity.setOrder(orderEntity.get());
        orderItemEntity.setItem(itemEntity.get());
        orderItemEntity.setObservation(orderItem.getObservation());
        orderItemEntity = this.orderItemJpaRepository.save(orderItemEntity);

        return OrderItemMapper.INSTANCE.toOrderItem(orderItemEntity);
    }

    @Override
    public void delete(Long orderId, Long itemId) {
        this.orderItemJpaRepository.deleteByOrderIdAndItemId(orderId, itemId);
    }
}
