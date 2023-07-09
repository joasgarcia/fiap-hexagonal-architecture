package com.fiap.restaurant.adapter.driven.data.repository.customer;

import com.fiap.restaurant.adapter.driven.data.entity.customer.CustomerEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.customer.CustomerMapper;
import com.fiap.restaurant.core.model.customer.Customer;
import com.fiap.restaurant.core.repository.customer.ICustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerRepository implements ICustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerRepository(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = CustomerMapper.INSTANCE.toCustomerEntity(customer);
        customerEntity = this.customerJpaRepository.save(customerEntity);

        if (customerEntity == null) return null;

        return CustomerMapper.INSTANCE.toCustomer(customerEntity);
    }

    @Override
    public Customer findById(Long id) {
        Optional<CustomerEntity> customerEntity = this.customerJpaRepository.findById(id);
        if (customerEntity.isEmpty()) return null;
        return CustomerMapper.INSTANCE.toCustomer(customerEntity.get());
    }

    @Override
    public Customer findByCpf(String cpf) {
        CustomerEntity customerEntity = this.customerJpaRepository.findByCpf(cpf);

        if (customerEntity == null) return null;

        return CustomerMapper.INSTANCE.toCustomer(customerEntity);
    }

    @Override
    public Customer findByCpfOrEmail(String cpf, String email) {
        CustomerEntity customerEntity = this.customerJpaRepository.findByCpfOrEmail(cpf, email);
        if (customerEntity == null) return null;

        return CustomerMapper.INSTANCE.toCustomer(customerEntity);
    }
}
