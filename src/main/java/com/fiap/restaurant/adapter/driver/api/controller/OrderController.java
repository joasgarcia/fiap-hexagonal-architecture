package com.fiap.restaurant.adapter.driver.api.controller;

import com.fiap.restaurant.core.dto.OrderDTO;
import com.fiap.restaurant.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public List<OrderDTO> list() {
        return orderService.list();
    }
}
