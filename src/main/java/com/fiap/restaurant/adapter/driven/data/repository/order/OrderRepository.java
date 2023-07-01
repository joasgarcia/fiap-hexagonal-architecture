package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.OrderMapper;
import com.fiap.restaurant.core.model.Order;
import com.fiap.restaurant.core.repository.IOrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
