package com.fiap.restaurant;

import com.fiap.restaurant.adapter.driven.data.repository.customer.CustomerRepository;
import com.fiap.restaurant.adapter.driven.data.repository.product.ProductRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.OrderRepository;
import com.fiap.restaurant.core.service.CustomerService;
import com.fiap.restaurant.core.service.ProductService;
import com.fiap.restaurant.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public OrderService orderService() {
        return new OrderService(this.orderRepository);
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(this.customerRepository);
    }

    @Bean
    public ProductService productService() {
        return new ProductService(this.productRepository);
    }
}
