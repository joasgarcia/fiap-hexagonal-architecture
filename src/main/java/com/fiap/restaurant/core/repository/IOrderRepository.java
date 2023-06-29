package com.fiap.restaurant.core.repository;

import com.fiap.restaurant.core.model.Order;

import java.util.List;

public interface IOrderRepository {

    List<Order> list();

}
