package com.fiap.restaurant.core.repository;

import com.fiap.restaurant.core.model.Customer;

public interface ICustomerRepository {

    Customer save(Customer customer);
    Customer findByCpf(String cpf);
}
