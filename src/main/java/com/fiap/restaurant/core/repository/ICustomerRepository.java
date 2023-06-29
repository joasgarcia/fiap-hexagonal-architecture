package com.fiap.restaurant.core.repository;

import com.fiap.restaurant.core.model.Customer;

public interface ICustomerRepository {

    Customer findByCpf(String cpf);
}
