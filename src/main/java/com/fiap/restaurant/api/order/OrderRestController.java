package com.fiap.restaurant.api.order;

import com.fiap.restaurant.controller.order.OrderController;
import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.types.dto.order.UpdateOrderStatusDTO;
import com.fiap.restaurant.types.dto.order.UpdatePaymentStatusDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(path = "/webhookPaymentStatus")
    public ResponseEntity<Object> updatePaymentStatus(@RequestBody UpdatePaymentStatusDTO updatePaymentStatusDTO) {
        try {
            OrderController.updatePaymentStatus(updatePaymentStatusDTO.getExternalIdentifier(), updatePaymentStatusDTO.getPaymentStatus(), this.orderDatabaseConnection);
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
            OrderController.updateStatus(id, updateOrderStatusDTO.getStatus(), this.orderDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
