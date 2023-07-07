package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.order.OrderMapper;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.repository.order.IOrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderRepository implements IOrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepository(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public List<Order> list() {
        List<OrderEntity> list = this.orderJpaRepository.findAll();
        return OrderMapper.INSTANCE.toOrderList(list);
    }

    @Override
    public Order findById(Long id) {
        Optional<OrderEntity> orderEntity = this.orderJpaRepository.findById(id);

        return OrderMapper.INSTANCE.toOrder(orderEntity);
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = this.orderJpaRepository.save(OrderMapper.INSTANCE.toOrderEntity(order));
        return OrderMapper.INSTANCE.toOrder(orderEntity);
    }
}
