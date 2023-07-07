package com.fiap.restaurant.adapter.driver.api.controller.login;

import com.fiap.restaurant.core.model.customer.Customer;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.service.customer.CustomerService;
import com.fiap.restaurant.core.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<Order> login(@RequestBody Optional<Customer> customer) {
        Customer loggedCustomer = null;
        if (customer.isPresent()) loggedCustomer = customerService.save(customer.get());

        Order order = this.orderService.save(loggedCustomer);
        return ResponseEntity.ok(order);
    }
}
