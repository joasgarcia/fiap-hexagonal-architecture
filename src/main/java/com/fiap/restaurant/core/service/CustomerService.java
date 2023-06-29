package com.fiap.restaurant.core.service;

import com.fiap.restaurant.core.model.Customer;
import com.fiap.restaurant.core.repository.ICustomerRepository;

public class CustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer findByCpf(String cpf) {
        return this.customerRepository.findByCpf(cpf);
    }
}
