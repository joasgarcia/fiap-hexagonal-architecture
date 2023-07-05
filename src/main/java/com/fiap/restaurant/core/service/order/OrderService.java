package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.repository.order.IOrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class OrderService {

    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> list() {
        return orderRepository.list();
    }
}