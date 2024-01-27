package com.fiap.restaurant.controller.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.customer.CustomerJpaRepository;
import com.fiap.restaurant.external.db.order.OrderJpa;
import com.fiap.restaurant.external.db.order.OrderJpaRepository;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.util.CustomerTestUtil;
import com.fiap.restaurant.util.OrderTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class OrderControllerIT {

    @Autowired
    private OrderDatabaseConnection orderDatabaseConnection;

    @Autowired
    private CustomerDatabaseConnection customerDatabaseConnection;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Test
    @Rollback
    void mustUpdateOrderStatus() {
        CustomerJpa customerJpa = customerJpaRepository.save(CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF));
        OrderStatus initialOrderStatus = OrderStatus.RECEIVED;
        OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, initialOrderStatus, OrderPaymentStatus.PENDING, new Date()));

        OrderStatus orderStatus = OrderStatus.PREPARING;
        Order order = OrderController.updateStatus(orderJpa.getId(), orderStatus.toString(), orderDatabaseConnection, customerDatabaseConnection);

        assertThat(order.getStatus()).isEqualTo(orderStatus);
        assertThat(order.getStatus()).isNotEqualTo(initialOrderStatus);
    }

    @Test
    @Rollback
    void mustUpdateOrderPaymentStatus() {
        CustomerJpa customerJpa = customerJpaRepository.save(CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF));
        OrderPaymentStatus initialOrderPaymentStatus = OrderPaymentStatus.PENDING;
        OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, initialOrderPaymentStatus, new Date()));

        OrderPaymentStatus orderPaymentStatus = OrderPaymentStatus.APPROVED;
        Order order = OrderController.updatePaymentStatus(orderJpa.getId(), orderPaymentStatus.toString(), orderDatabaseConnection, customerDatabaseConnection);

        assertThat(order.getPaymentStatus()).isEqualTo(orderPaymentStatus);
        assertThat(order.getPaymentStatus()).isNotEqualTo(initialOrderPaymentStatus);
    }

    @Test
    @Rollback
    void mustFindOrderById() {
        CustomerJpa customerJpa = customerJpaRepository.save(CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF));
        OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date()));

        Order order = OrderController.findById(orderJpa.getId(), orderDatabaseConnection, customerDatabaseConnection);
        assertThat(order).isNotNull();
        assertThat(order.getStatus()).isEqualTo(orderJpa.getStatus());
        assertThat(order.getPaymentStatus()).isEqualTo(orderJpa.getPaymentStatus());
        assertThat(order.getDateCreated()).isEqualTo(orderJpa.getDateCreated());
        assertThat(order.getCustomer().getName()).isEqualTo(customerJpa.getName());
        assertThat(order.getCustomer().getEmail()).isEqualTo(customerJpa.getEmail());
        assertThat(order.getCustomer().getCpf()).isEqualTo(customerJpa.getCpf());
    }

    @Test
    @Rollback
    void mustThrowExceptionOrderIdNotFound() {
        final Long nonexistentOrderId = 0L;

        assertThatThrownBy(() -> OrderController.findById(nonexistentOrderId, orderDatabaseConnection, customerDatabaseConnection))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Pedido não encontrado");
    }
}
