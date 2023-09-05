package com.fiap.restaurant.cleanarchitecture.external.db.order;

import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderItemDatabaseConnection;
import org.springframework.stereotype.Component;

@Component
public class OrderItemJpaConnection implements OrderItemDatabaseConnection {

    private final CleanOrderItemJpaRepository orderItemJpaRepository;

    public OrderItemJpaConnection(CleanOrderItemJpaRepository orderItemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
    }
    @Override
    public Object save(OrderItemJpa orderItemJpa) {
        return this.orderItemJpaRepository.save(orderItemJpa);
    }
}
