package com.fiap.restaurant.api.order;

import com.fiap.restaurant.controller.order.OrderController;
import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.messagebroker.MessageBroker;
import com.fiap.restaurant.external.messagebroker.SqsMessageBroker;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderItemDatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderRestController {

    private final OrderDatabaseConnection orderDatabaseConnection;

    private final CustomerDatabaseConnection customerDatabaseConnection;

    private final ItemDatabaseConnection itemDatabaseConnection;

    private final OrderItemDatabaseConnection orderItemDatabaseConnection;

    public OrderRestController(OrderDatabaseConnection orderDatabaseConnection, CustomerDatabaseConnection customerDatabaseConnection, ItemDatabaseConnection itemDatabaseConnection, OrderItemDatabaseConnection orderItemDatabaseConnection) {
        this.orderDatabaseConnection = orderDatabaseConnection;
        this.customerDatabaseConnection = customerDatabaseConnection;
        this.itemDatabaseConnection = itemDatabaseConnection;
        this.orderItemDatabaseConnection = orderItemDatabaseConnection;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            Order order = OrderController.findById(id, orderDatabaseConnection, customerDatabaseConnection);
            return ResponseEntity.ok().body(order);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<Object> checkout(@RequestBody SaveOrderDTO saveOrderDTO) {
        try {
            final MessageBroker messageBroker = new SqsMessageBroker("payment-q");

            Order order = OrderController.save(saveOrderDTO, this.orderDatabaseConnection, this.customerDatabaseConnection, this.itemDatabaseConnection, this.orderItemDatabaseConnection, messageBroker);
            return ResponseEntity.ok(order);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/finish/{id}")
    public ResponseEntity<Object> finish(@PathVariable("id") Long id) {
        try {
            OrderController.updateStatus(id, OrderStatus.FINISHED, this.orderDatabaseConnection, this.customerDatabaseConnection);
            return ResponseEntity.ok(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
