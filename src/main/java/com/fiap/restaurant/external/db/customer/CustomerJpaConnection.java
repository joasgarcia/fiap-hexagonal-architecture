package com.fiap.restaurant.external.db.customer;

import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaConnection implements CustomerDatabaseConnection<CustomerJpa> {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerJpaConnection(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public void save(CustomerJpa customerJpa) {
        this.customerJpaRepository.save(customerJpa);
    }

    @Override
    public CustomerJpa getById(Long id) {
        return this.customerJpaRepository.getReferenceById(id);
    }

    public CustomerJpa findByEmail(String email) {
        return this.customerJpaRepository.findByEmail(email);
    }

    @Override
    public CustomerJpa findByCpf(String cpf) {
        return this.customerJpaRepository.findByCpf(cpf);
    }
}
