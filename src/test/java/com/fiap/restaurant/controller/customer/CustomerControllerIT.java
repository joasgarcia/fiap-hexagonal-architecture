package com.fiap.restaurant.controller.customer;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.customer.CustomerJpaRepository;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.util.CustomerTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class CustomerControllerIT {

    @Autowired
    private CustomerDatabaseConnection customerDatabaseConnection;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Test
    @Rollback
    void mustSaveCustomer() {
        final SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@email.com", UUID.randomUUID().toString());

        CustomerController.save(saveCustomerDTO, customerDatabaseConnection);

        CustomerJpa customerJpa = customerJpaRepository.findByEmail(saveCustomerDTO.getEmail());
        assertThat(customerJpa).isNotNull();
        assertThat(customerJpa.getName()).isEqualTo(saveCustomerDTO.getName());
        assertThat(customerJpa.getEmail()).isEqualTo(saveCustomerDTO.getEmail());
        assertThat(customerJpa.getCpf()).isEqualTo(saveCustomerDTO.getCpf());
    }

    @Test
    @Rollback
    void mustFindCustomerByCpf() {
        final String customerCpf = "71841727016";
        final String customerName = "John Doe";
        final String customerEmail = "johndoe@email.com";
        final CustomerJpa customerJpa = CustomerTestUtil.generateJpa(customerName, customerEmail, customerCpf);
        customerJpaRepository.save(customerJpa);

        Customer customer = CustomerController.findByCpf(customerCpf, customerDatabaseConnection);
        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(customerName);
        assertThat(customer.getCpf()).isEqualTo(customerCpf);
        assertThat(customer.getEmail()).isEqualTo(customerEmail);
    }

    @Test
    @Rollback
    void mustThrowExceptionCustomerNotFoundOnFindCustomerByCpf() {
        final String nonexistentCustomerCpf = "71841727016";

        assertThatThrownBy(() -> CustomerController.findByCpf(nonexistentCustomerCpf, customerDatabaseConnection))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cliente não encontrado");
    }
}
