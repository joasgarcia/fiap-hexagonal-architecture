package com.fiap.restaurant.external.db.customer;

import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer.CustomerDatabaseConnection;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaConnection implements CustomerDatabaseConnection<CustomerJpa> {

    private final CleanCustomerJpaRepository customerJpaRepository;

    public CustomerJpaConnection(CleanCustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public void save(CustomerJpa customerJpa) {
        this.customerJpaRepository.save(customerJpa);
    }

    public CustomerJpa findByEmail(String email) {
        return this.customerJpaRepository.findByEmail(email);
    }

    @Override
    public CustomerJpa findByCpf(String cpf) {
        return this.customerJpaRepository.findByCpf(cpf);
    }
}
