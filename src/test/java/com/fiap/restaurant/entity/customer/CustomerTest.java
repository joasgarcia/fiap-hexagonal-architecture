package com.fiap.restaurant.entity.customer;

import com.fiap.restaurant.util.CustomerTestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    @Test
    void mustInstantiateCorrectly() {
        final String name = "Customer name";
        final String email = "customer@teste.com";
        final String cpf = CustomerTestUtil.CPF;
        final Long id = 1L;

        Customer customer = new Customer(name, email, cpf);
        customer.setId(id);

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getCpf()).isEqualTo(cpf);
    }
}
