package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.customer.CustomerEntity;
import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.order.OrderMapper;
import com.fiap.restaurant.adapter.driven.data.repository.customer.CustomerJpaRepository;
import com.fiap.restaurant.core.exception.ResourceNotFoundException;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.repository.order.IOrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderRepository implements IOrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final CustomerJpaRepository customerJpaRepository;

    public OrderRepository(OrderJpaRepository orderJpaRepository, CustomerJpaRepository customerJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
        this.customerJpaRepository = customerJpaRepository;
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
        OrderEntity orderEntity = OrderMapper.INSTANCE.toOrderEntity(order);

        Long customerId = order.getCustomer().getId();
        Optional<CustomerEntity> customerEntity = this.customerJpaRepository.findById(customerId);
        if (customerEntity.isEmpty()) throw new ResourceNotFoundException("Cliente [" + customerId + "] não encontrado");
        orderEntity.setCustomer(customerEntity.get());

        orderEntity = this.orderJpaRepository.save(orderEntity);
        return OrderMapper.INSTANCE.toOrder(orderEntity);
    }
}
