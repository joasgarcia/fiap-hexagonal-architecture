package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.OrderItemJpa;
import com.fiap.restaurant.external.db.order.OrderJpa;
import com.fiap.restaurant.types.dto.order.OrderItemDTO;

public class OrderItemTestUtil {

    public static OrderItemJpa generateJpa(OrderJpa order, ItemJpa item, String observation) {
        OrderItemJpa orderItemJpa = new OrderItemJpa();
        orderItemJpa.setOrder(order);
        orderItemJpa.setItem(item);
        orderItemJpa.setObservation(observation);

        return orderItemJpa;
    }

    public static OrderItemDTO generateDTO(Long itemId, String observation) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setItemId(itemId);
        orderItemDTO.setObservation(observation);

        return orderItemDTO;
    }
}
