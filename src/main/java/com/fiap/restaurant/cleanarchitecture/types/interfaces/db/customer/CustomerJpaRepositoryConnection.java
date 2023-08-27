package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer;


import com.fiap.restaurant.cleanarchitecture.external.db.customer.CustomerJpa;

public interface CustomerJpaRepositoryConnection {

    void save(CustomerJpa customerJpa);

    CustomerJpa findByEmail(String email);

    CustomerJpa findByCpf(String cpf);
}
