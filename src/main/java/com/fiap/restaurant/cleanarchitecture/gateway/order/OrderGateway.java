package com.fiap.restaurant.cleanarchitecture.gateway.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.Order;
import com.fiap.restaurant.cleanarchitecture.external.db.order.OrderJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.order.OrderMapper;

public class OrderGateway implements IOrderGateway {

    private final OrderDatabaseConnection orderDatabaseConnection;

    public OrderGateway(OrderDatabaseConnection orderDatabaseConnection) {
        this.orderDatabaseConnection = orderDatabaseConnection;
    }

    @Override
    public Order getById(Long id) {
        OrderJpa orderJpa = (OrderJpa) this.orderDatabaseConnection.getById(id);
        if (orderJpa == null) return null;

        return OrderMapper.INSTANCE.toOrder(orderJpa);
    }
}
