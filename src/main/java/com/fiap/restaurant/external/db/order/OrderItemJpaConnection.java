package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.types.interfaces.db.order.OrderItemDatabaseConnection;
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
