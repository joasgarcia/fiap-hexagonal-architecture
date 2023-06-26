package com.fiap.restaurant.adapter.driven.data.repository;

import com.fiap.restaurant.core.dto.OrderDTO;
import com.fiap.restaurant.core.model.Order;
import com.fiap.restaurant.core.repository.IOrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepository implements IOrderRepository {

    @Override
    public List<OrderDTO> list() {
        OrderDTO orderDTO = new OrderDTO(1L, new Date());

        List<OrderDTO> list = new ArrayList<>();
        list.add(orderDTO);

        return list;
    }
}
