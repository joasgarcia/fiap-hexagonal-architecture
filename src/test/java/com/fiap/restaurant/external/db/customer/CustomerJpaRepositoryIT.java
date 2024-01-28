package com.fiap.restaurant.external.db.customer;

import com.fiap.restaurant.util.CustomerTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class CustomerJpaRepositoryIT {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveCustomer() {
            CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF);
            CustomerJpa savedCustomer = customerJpaRepository.save(customerJpa);
            assertThat(savedCustomer).isEqualTo(customerJpa);
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindCustomerByCpf() {
            CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF);
            CustomerJpa savedCustomer = customerJpaRepository.save(customerJpa);
            CustomerJpa foundCustomer = customerJpaRepository.findByCpf(customerJpa.getCpf());
            assertThat(foundCustomer.getCpf()).isNotEmpty().isEqualTo(savedCustomer.getCpf());
        }

        @Test
        @Rollback
        void mustNotFindCustomerByCpf() {
            CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", CustomerTestUtil.CPF);
            CustomerJpa customer = customerJpaRepository.findByCpf(customerJpa.getCpf());
            assertThat(customer).isNull();
        }
    }
}
