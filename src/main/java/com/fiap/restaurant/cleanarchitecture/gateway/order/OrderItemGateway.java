package com.fiap.restaurant.cleanarchitecture.gateway.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.OrderItem;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.order.OrderItemJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.order.OrderJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderItemDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.order.OrderItemMapper;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

public class OrderItemGateway implements IOrderItemGateway {

    private final OrderItemDatabaseConnection orderItemDatabaseConnection;
    private final ItemDatabaseConnection itemDatabaseConnection;
    private final OrderDatabaseConnection orderDatabaseConnection;

    public OrderItemGateway(OrderItemDatabaseConnection orderItemDatabaseConnection, ItemDatabaseConnection itemDatabaseConnection, OrderDatabaseConnection orderDatabaseConnection) {
        this.orderItemDatabaseConnection = orderItemDatabaseConnection;
        this.itemDatabaseConnection = itemDatabaseConnection;
        this.orderDatabaseConnection = orderDatabaseConnection;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItemJpa orderItemJpa = new OrderItemJpa();
        BeanUtils.copyProperties(orderItem, orderItemJpa, "id");

        OrderJpa orderJpa = (OrderJpa) orderDatabaseConnection.getById(orderItem.getOrder().getId());
        orderItemJpa.setOrder(orderJpa);

        Optional<ItemJpa> itemJpa = itemDatabaseConnection.getById(orderItem.getItem().getId());
        orderItemJpa.setItem(itemJpa.get());

        orderItemJpa = (OrderItemJpa) this.orderItemDatabaseConnection.save(orderItemJpa);

        return OrderItemMapper.INSTANCE.toOrderItem(orderItemJpa);
    }
}
