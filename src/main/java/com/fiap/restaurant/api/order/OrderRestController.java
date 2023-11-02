package com.fiap.restaurant.api.order;

import com.fiap.restaurant.controller.order.OrderController;
import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.dto.order.OrderScreenPresenterDTO;
import com.fiap.restaurant.types.dto.order.UpdateOrderStatusDTO;
import com.fiap.restaurant.types.dto.order.UpdatePaymentStatusDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderItemDatabaseConnection;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


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
        } catch (EntityNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/webhookPaymentStatus")
    public ResponseEntity<Object> updatePaymentStatus(@RequestBody UpdatePaymentStatusDTO updatePaymentStatusDTO) {
        try {
            OrderController.updatePaymentStatus(updatePaymentStatusDTO.getExternalIdentifier(), updatePaymentStatusDTO.getPaymentStatus(), this.orderDatabaseConnection, this.customerDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/{id}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        try {
            OrderController.updateStatus(id, updateOrderStatusDTO.getStatus(), this.orderDatabaseConnection, this.customerDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/statusScreen")
    public ResponseEntity<Object> statusScreen() {
        try {
            List<OrderScreenPresenterDTO> orderScreenPresenterDTOList = OrderController.listOrderedByStatus(this.orderDatabaseConnection, this.customerDatabaseConnection);
            return ResponseEntity.ok(orderScreenPresenterDTOList);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<Object> checkout(@RequestBody SaveOrderDTO saveOrderDTO) {
        try {
            Order order = OrderController.save(saveOrderDTO, this.orderDatabaseConnection, this.customerDatabaseConnection, this.itemDatabaseConnection, this.orderItemDatabaseConnection);
            return ResponseEntity.ok(order);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
