package com.fiap.restaurant.adapter.driver.api.controller.order;

import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}/item")
    public ResponseEntity<Boolean> addItem(@PathVariable("id") Long orderId, Long itemId) {
        this.orderService.addItem(orderId, itemId);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}/item/{itemId}")
    public ResponseEntity<Boolean> removeItem(@PathVariable("id") Long orderId, @PathVariable("itemId") Long itemId) {
        this.orderService.removeItem(orderId, itemId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/pay/{id}")
    public Boolean pay(@PathVariable("id") Long id) {
        System.out.println("IDDDDDDDDDDD :" + id);
        return orderService.pay(id);
    }
}
