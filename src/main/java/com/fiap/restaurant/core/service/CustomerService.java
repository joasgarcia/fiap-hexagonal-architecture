package com.fiap.restaurant.core.service;

import com.fiap.restaurant.adapter.driven.data.entity.CustomerEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.CustomerMapper;
import com.fiap.restaurant.core.model.Customer;
import com.fiap.restaurant.core.repository.ICustomerRepository;

import java.util.Optional;

public class CustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findByCpf(String cpf) {
        Optional<CustomerEntity> customerEntity = customerRepository.findByCpf(cpf);

        if (customerEntity.isEmpty()) return Optional.empty();

        return Optional.of(CustomerMapper.toCustomer(customerEntity.get()));
    }
}
