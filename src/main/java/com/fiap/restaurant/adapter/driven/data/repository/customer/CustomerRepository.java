package com.fiap.restaurant.adapter.driven.data.repository.customer;

import com.fiap.restaurant.adapter.driven.data.entity.customer.CustomerEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.CustomerMapper;
import com.fiap.restaurant.core.model.Customer;
import com.fiap.restaurant.core.repository.ICustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository implements ICustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerRepository(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = CustomerMapper.toCustomerEntity(customer);
        customerEntity = this.customerJpaRepository.save(customerEntity);

        if (customerEntity == null) return null;

        return CustomerMapper.toCustomer(customerEntity);
    }

    @Override
    public Customer findByCpf(String cpf) {
        CustomerEntity customerEntity = this.customerJpaRepository.findByCpf(cpf);

        if (customerEntity == null) return null;

        return CustomerMapper.toCustomer(customerEntity);
    }
}
