package com.fiap.restaurant.gateway.customer;

import com.fiap.restaurant.entity.customer.Customer;

public interface ICustomerGateway {

    void save(Customer customer);

    void update(Customer customer);

    Customer findByEmail(String email);

    Customer findByCpf(String cpf);
}
