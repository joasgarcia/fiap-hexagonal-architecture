package com.fiap.restaurant.cleanarchitecture.gateway;

import com.fiap.restaurant.cleanarchitecture.entity.Customer;

public interface ICustomerGateway {

    void save(Customer customer);

    Customer findByEmail(String email);

    Customer findByCpf(String cpf);
}
