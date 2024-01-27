package com.fiap.restaurant.entity.customer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    @Test
    void mustInstantiateCorrectly() {
        final String name = "Customer name";
        final String email = "customer@teste.com";
        final String cpf = "71841727016";

        Customer customer = new Customer(name, email, cpf);
        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getCpf()).isEqualTo(cpf);
    }
}
