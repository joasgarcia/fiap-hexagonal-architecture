package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.util.CustomerTestUtil;
import com.fiap.restaurant.util.OrderTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class OrderJpaRepositoryIT {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveOrder() {
            CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", UUID.randomUUID().toString());
            OrderJpa orderJpa = OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date());
            OrderJpa persistedOrderJpa = orderJpaRepository.save(orderJpa);
            assertThat(persistedOrderJpa).isNotNull();
            assertThat(persistedOrderJpa.getStatus()).isEqualTo(orderJpa.getStatus());
            assertThat(persistedOrderJpa.getPaymentStatus()).isEqualTo(orderJpa.getPaymentStatus());
            assertThat(persistedOrderJpa.getDateCreated()).isEqualTo(orderJpa.getDateCreated());
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindOrderById() {
            CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", UUID.randomUUID().toString());
            OrderJpa orderJpa1 = OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date());
            OrderJpa persistedOrderJpa1 = orderJpaRepository.save(orderJpa1);

            OrderJpa orderJpa2 = OrderTestUtil.generateJpa(customerJpa, OrderStatus.PREPARING, OrderPaymentStatus.APPROVED, new Date());
            OrderJpa persistedOrderJpa2 = orderJpaRepository.save(orderJpa2);

            Optional<OrderJpa> optionalOrderJpa1 = orderJpaRepository.findById(persistedOrderJpa1.getId());
            assertThat(optionalOrderJpa1).isPresent();

            Optional<OrderJpa> optionalOrderJpa2 = orderJpaRepository.findById(persistedOrderJpa2.getId());
            assertThat(optionalOrderJpa2).isPresent();

            Optional<OrderJpa> optionalOrderJpa3 = orderJpaRepository.findById(0L);
            assertThat(optionalOrderJpa3).isNotPresent();
        }
    }
}
