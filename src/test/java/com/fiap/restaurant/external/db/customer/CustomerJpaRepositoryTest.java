package com.fiap.restaurant.external.db.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerJpaRepositoryTest {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    private CustomerJpa customerJpa;

    @BeforeEach
    void setup() {
        customerJpa = new CustomerJpa();
        customerJpa.setName("John Doe");
        customerJpa.setEmail("johndoe@email.com");
        customerJpa.setCpf(UUID.randomUUID().toString());
    }

    @Nested
    class SaveCustomer {
        @Test
        void mustSaveCustomer() {
            CustomerJpa savedCustomer = customerJpaRepository.save(customerJpa);
            assertThat(savedCustomer).isEqualTo(customerJpa);
        }
    }

    @Nested
    class FindCustomer {
        @Test
        void mustFindCustomerByCpf() {
            CustomerJpa savedCustomer = customerJpaRepository.save(customerJpa);
            CustomerJpa foundCustomer = customerJpaRepository.findByCpf(customerJpa.getCpf());
            assertThat(foundCustomer.getCpf()).isNotEmpty().isEqualTo(savedCustomer.getCpf());
        }

        @Test
        void mustNotFindCustomerByCpf() {
            CustomerJpa customer = customerJpaRepository.findByCpf(customerJpa.getCpf());
            assertThat(customer).isNull();
        }
    }
}
