package com.fiap.restaurant.usecase.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.order.OrderJpa;
import com.fiap.restaurant.external.db.order.OrderJpaRepository;
import com.fiap.restaurant.gateway.order.IOrderGateway;
import com.fiap.restaurant.gateway.order.OrderGateway;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.util.CustomerTestUtil;
import com.fiap.restaurant.util.OrderTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
public class OrderUseCaseIT {

    @Autowired
    private OrderDatabaseConnection orderDatabaseConnection;

    @Autowired
    private CustomerDatabaseConnection customerDatabaseConnection;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    private IOrderGateway orderGateway;

    @BeforeEach
    void setup() {
        this.orderGateway = new OrderGateway(orderDatabaseConnection, customerDatabaseConnection);
    }

    @Test
    @Rollback
    void mustUpdateOrderStatus() {
        final CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF);

        OrderStatus orderStatus = OrderStatus.RECEIVED;
        final OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, orderStatus, OrderPaymentStatus.PENDING, new Date()));

        orderStatus = OrderStatus.PREPARING;
        Order order = OrderUseCase.updateStatus(orderJpa.getId(), orderStatus, orderGateway);

        assertThat(order.getStatus()).isEqualTo(orderStatus);
    }

    @Test
    @Rollback
    void mustUpdateOrderPaymentStatus() {
        final CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF);

        OrderPaymentStatus orderPaymentStatus = OrderPaymentStatus.PENDING;
        final OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, orderPaymentStatus, new Date()));

        orderPaymentStatus = OrderPaymentStatus.APPROVED;
        Order order = OrderUseCase.updatePaymentStatus(orderJpa.getId(), orderPaymentStatus, orderGateway);

        assertThat(order.getPaymentStatus()).isEqualTo(orderPaymentStatus);
    }

    @Test
    @Rollback
    void mustFindOrderById() {
        CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF);
        OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date()));

        Order order = OrderUseCase.findById(orderJpa.getId(), orderGateway);
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
        assertThatThrownBy(() -> OrderUseCase.findById(nonexistentOrderId, orderGateway))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Pedido não encontrado");
    }

}
