package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.order.OrderJpa;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.types.mapper.order.OrderMapper;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class OrderGateway implements IOrderGateway {

    private final OrderDatabaseConnection orderDatabaseConnection;
    private final CustomerDatabaseConnection customerDatabaseConnection;

    public OrderGateway(OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection) {
        this.orderDatabaseConnection = orderDatabaseConnection;
        this.customerDatabaseConnection = customerDatabaseConnection;
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

    @Override
    public Order save(Order order) {
        OrderJpa orderJpa = new OrderJpa();

        CustomerJpa customerJpa = (CustomerJpa) this.customerDatabaseConnection.findByCpf(order.getCustomer().getCpf());
        orderJpa.setCustomer(customerJpa);

        BeanUtils.copyProperties(order, orderJpa, "id");
        OrderJpa orderJpaCreated = (OrderJpa) this.orderDatabaseConnection.save(orderJpa);

        return OrderMapper.INSTANCE.toOrder(orderJpaCreated);
    }

    @Override
    public List<Order> listOrderedByStatusAndId() {
        List<OrderJpa> orderJpaList = this.orderDatabaseConnection.listOrderedByStatusAndId();
        return OrderMapper.INSTANCE.toOrderList(orderJpaList);
    }
}
