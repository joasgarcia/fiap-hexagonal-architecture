package com.fiap.restaurant.cleanarchitecture.external.db.order;

import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderItemDatabaseConnection;
import org.springframework.stereotype.Component;

@Component
public class OrderItemJpaConnection implements OrderItemDatabaseConnection {

    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderItemJpaConnection(OrderItemJpaRepository orderItemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
    }
    @Override
    public Object save(OrderItemJpa orderItemJpa) {
        return this.orderItemJpaRepository.save(orderItemJpa);
    }
}
