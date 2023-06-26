package com.fiap.restaurant.core.service;

import com.fiap.restaurant.core.dto.OrderDTO;
import com.fiap.restaurant.core.repository.IOrderRepository;
import jdk.swing.interop.LightweightContentWrapper;

import java.util.List;

public class OrderService {

    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> list() {
        return orderRepository.list();
    }
}
