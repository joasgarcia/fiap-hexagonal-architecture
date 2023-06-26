package com.fiap.restaurant.core.repository;

import com.fiap.restaurant.core.dto.OrderDTO;

import java.util.List;

public interface IOrderRepository {

    List<OrderDTO> list();

}
