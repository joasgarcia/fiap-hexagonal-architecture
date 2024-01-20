package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.customer.CustomerJpa;

import java.util.UUID;

public class CustomerTestUtil {

    public static CustomerJpa generateJpa(String name, String email, String cnpj) {
        CustomerJpa customerJpa = new CustomerJpa();
        customerJpa.setName("John Doe");
        customerJpa.setEmail("johndoe@email.com");
        customerJpa.setCpf(UUID.randomUUID().toString());

        return customerJpa;
    }
}
