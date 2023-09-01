package com.fiap.restaurant.cleanarchitecture.gateway.customer;

import com.fiap.restaurant.cleanarchitecture.entity.customer.Customer;

public interface ICustomerGateway {

    void save(Customer customer);

    Customer findByEmail(String email);

    Customer findByCpf(String cpf);
}
