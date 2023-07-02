package com.fiap.restaurant.adapter.driver.api.controller.order;

import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.service.order.OrderService;
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
    public List<Order> list() {
        return orderService.list();
    }
}
