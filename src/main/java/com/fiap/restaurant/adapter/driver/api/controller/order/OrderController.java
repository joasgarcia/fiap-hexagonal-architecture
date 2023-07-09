package com.fiap.restaurant.adapter.driver.api.controller.order;

import com.fiap.restaurant.adapter.driver.api.controller.order.dto.OrderAddItemDTO;
import com.fiap.restaurant.core.model.customer.Customer;
import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.service.customer.CustomerService;
import com.fiap.restaurant.core.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public List<Order> list() {
        return orderService.list();
    }

    @PostMapping("/start")
    public ResponseEntity<Order> start(@RequestBody Optional<Customer> customer) {
        Customer loggedCustomer = null;
        if (customer.isPresent()) loggedCustomer = customerService.findOrCreate(customer.get());

        Order order = this.orderService.save(loggedCustomer);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/item")
    public ResponseEntity<Boolean> addItem(@PathVariable("id") Long orderId, @RequestBody OrderAddItemDTO dto) {
        this.orderService.addItem(orderId, dto.getItemId(), dto.getObservation());
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}/item/{itemId}")
    public ResponseEntity<Boolean> removeItem(@PathVariable("id") Long orderId, @PathVariable("itemId") Long itemId) {
        this.orderService.removeItem(orderId, itemId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/pay/{id}")
    public Boolean pay(@PathVariable("id") Long id) {
        return orderService.pay(id);
    }
}
