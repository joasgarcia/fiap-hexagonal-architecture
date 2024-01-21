package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.customer.CustomerJpaRepository;
import com.fiap.restaurant.util.CustomerTestUtil;
import com.fiap.restaurant.util.ItemTestUtil;
import com.fiap.restaurant.util.OrderItemTestUtil;
import com.fiap.restaurant.util.OrderTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class OrderItemJpaRepositoryIT {

    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Test
    @Rollback
    void mustSaveOrderItem() {
        final CustomerJpa customerJpa = customerJpaRepository.save(CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", UUID.randomUUID().toString()));
        final OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date()));
        final ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 12.5));

        final String orderItemObservation = "Observation";
        OrderItemJpa orderItemJpa = OrderItemTestUtil.generateJpa(orderJpa, itemJpa, orderItemObservation);
        OrderItemJpa persistedOrderItemJpa = orderItemJpaRepository.save(orderItemJpa);
        assertThat(persistedOrderItemJpa).isNotNull();
        assertThat(persistedOrderItemJpa.getItem().getId()).isEqualTo(itemJpa.getId());
        assertThat(persistedOrderItemJpa.getItem().getName()).isEqualTo(itemJpa.getName());
        assertThat(persistedOrderItemJpa.getItem().getDescription()).isEqualTo(itemJpa.getDescription());
        assertThat(persistedOrderItemJpa.getItem().getPrice()).isEqualTo(itemJpa.getPrice());
        assertThat(persistedOrderItemJpa.getOrder().getId()).isEqualTo(orderJpa.getId());
        assertThat(persistedOrderItemJpa.getOrder().getStatus()).isEqualTo(orderJpa.getStatus());
        assertThat(persistedOrderItemJpa.getOrder().getPaymentStatus()).isEqualTo(orderJpa.getPaymentStatus());
        assertThat(persistedOrderItemJpa.getOrder().getDateCreated()).isEqualTo(orderJpa.getDateCreated());
        assertThat(persistedOrderItemJpa.getOrder().getCustomer().getId()).isEqualTo(customerJpa.getId());
        assertThat(persistedOrderItemJpa.getOrder().getCustomer().getName()).isEqualTo(customerJpa.getName());
        assertThat(persistedOrderItemJpa.getOrder().getCustomer().getEmail()).isEqualTo(customerJpa.getEmail());
        assertThat(persistedOrderItemJpa.getObservation()).isEqualTo(orderItemObservation);
    }

}
