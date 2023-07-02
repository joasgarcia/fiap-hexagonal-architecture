package com.fiap.restaurant.core.repository.customer;

import com.fiap.restaurant.core.model.customer.Customer;

public interface ICustomerRepository {

    Customer save(Customer customer);
    Customer findByCpf(String cpf);
}
