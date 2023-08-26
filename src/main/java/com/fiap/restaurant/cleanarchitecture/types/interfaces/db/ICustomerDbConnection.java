package com.fiap.restaurant.cleanarchitecture.types.interfaces.db;


import com.fiap.restaurant.cleanarchitecture.api.db.jpa.CustomerJpa;

public interface ICustomerDbConnection {

    void save(CustomerJpa customerJpa);

    CustomerJpa findByEmail(String email);

    CustomerJpa findByCpf(String cpf);
}
