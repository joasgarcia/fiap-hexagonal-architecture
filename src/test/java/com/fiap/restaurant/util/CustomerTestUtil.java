package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.customer.CustomerJpa;

import java.util.UUID;

public class CustomerTestUtil {

    public static CustomerJpa generateJpa(String name, String email, String cpf) {
        CustomerJpa customerJpa = new CustomerJpa();
        customerJpa.setName(name);
        customerJpa.setEmail(email);
        customerJpa.setCpf(cpf);

        return customerJpa;
    }
}
