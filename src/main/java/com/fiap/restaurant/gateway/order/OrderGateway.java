package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.external.db.order.OrderJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.order.OrderMapper;
import org.springframework.beans.BeanUtils;

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

    @Override
    public Order update(Order order) {
        OrderJpa orderJpa = (OrderJpa) this.orderDatabaseConnection.getById(order.getId());

        BeanUtils.copyProperties(order, orderJpa, "id");
        OrderJpa updatedOrderJpa = (OrderJpa) this.orderDatabaseConnection.save(orderJpa);

        return OrderMapper.INSTANCE.toOrder(updatedOrderJpa);
    }
}
