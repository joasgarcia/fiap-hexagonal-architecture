package com.fiap.restaurant.cleanarchitecture.api.db;

import com.fiap.restaurant.cleanarchitecture.api.db.jpa.CustomerDbRepository;
import com.fiap.restaurant.cleanarchitecture.api.db.jpa.CustomerJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.ICustomerDbConnection;
import org.springframework.stereotype.Component;

@Component
public class CustomerDbConnection implements ICustomerDbConnection {

    private final CustomerDbRepository customerDbRepository;

    public CustomerDbConnection(CustomerDbRepository customerDbRepository) {
        this.customerDbRepository = customerDbRepository;
    }

    @Override
    public void save(CustomerJpa customerJpa) {
        this.customerDbRepository.save(customerJpa);
    }

    public CustomerJpa findByEmail(String email) {
        return this.customerDbRepository.findByEmail(email);
    }

    @Override
    public CustomerJpa findByCpf(String cpf) {
        return this.customerDbRepository.findByCpf(cpf);
    }
}
