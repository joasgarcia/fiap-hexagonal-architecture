package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.OrderItemJpa;
import com.fiap.restaurant.external.db.order.OrderJpa;

public class OrderItemTestUtil {

    public static OrderItemJpa generateJpa(OrderJpa order, ItemJpa item, String observation) {
        OrderItemJpa orderItemJpa = new OrderItemJpa();
        orderItemJpa.setOrder(order);
        orderItemJpa.setItem(item);
        orderItemJpa.setObservation(observation);

        return orderItemJpa;
    }
}
