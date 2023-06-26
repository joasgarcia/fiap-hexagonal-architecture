package com.fiap.restaurant;

import com.fiap.restaurant.adapter.driven.data.repository.OrderRepository;
import com.fiap.restaurant.core.repository.IOrderRepository;
import com.fiap.restaurant.core.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IOrderRepository orderRepository() {
        return new OrderRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository());
    }
}
