package com.fiap.restaurant.core.repository.order;

import com.fiap.restaurant.core.model.order.Order;

import java.util.List;

public interface IOrderRepository {

    List<Order> list();

}
