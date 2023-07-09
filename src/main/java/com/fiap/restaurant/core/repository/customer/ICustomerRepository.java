package com.fiap.restaurant.core.repository.customer;

import com.fiap.restaurant.core.model.customer.Customer;

public interface ICustomerRepository {

    Customer save(Customer customer);
    Customer findById(Long id);
    Customer findByCpf(String cpf);
    Customer findByCpfOrEmail(String cpf, String email);
}
