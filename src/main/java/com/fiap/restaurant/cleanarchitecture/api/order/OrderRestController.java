package com.fiap.restaurant.cleanarchitecture.api.order;

import com.fiap.restaurant.cleanarchitecture.controller.order.OrderController;
import com.fiap.restaurant.cleanarchitecture.entity.order.Order;
import com.fiap.restaurant.cleanarchitecture.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderDatabaseConnection;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ca/order")
public class OrderRestController {

    private final OrderDatabaseConnection orderDatabaseConnection;

    public OrderRestController(OrderDatabaseConnection orderDatabaseConnection) {
        this.orderDatabaseConnection = orderDatabaseConnection;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            Order order = OrderController.findById(id, orderDatabaseConnection);

            return ResponseEntity.ok().body(order);
        } catch (EntityNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
