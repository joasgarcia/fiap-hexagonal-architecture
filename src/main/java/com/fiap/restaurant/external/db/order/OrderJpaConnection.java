package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderJpaConnection implements OrderDatabaseConnection<OrderJpa> {

    private final OrderJpaRepository orderJpaRepository;

    public OrderJpaConnection(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public OrderJpa getById(Long id) {
        return orderJpaRepository.getReferenceById(id);
    }

    @Override
    public OrderJpa save(OrderJpa orderJpa) {
        return this.orderJpaRepository.save(orderJpa);
    }

    @Override
    public List<OrderJpa> listOrderedByStatusAndId() {
        return this.orderJpaRepository.listOrderedByStatusAndId();
    }
}
