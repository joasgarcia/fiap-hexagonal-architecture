package com.fiap.restaurant.adapter.driven.data.mapper;

import com.fiap.restaurant.adapter.driven.data.entity.CustomerEntity;
import com.fiap.restaurant.core.model.Customer;

public class CustomerMapper {

    public static Customer toCustomer(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        customer.setId(customerEntity.getId());
        customer.setName(customerEntity.getName());
        customer.setCpf(customerEntity.getCpf());

        return customer;
    }

    public static CustomerEntity toCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setCpf(customer.getCpf());

        return customerEntity;
    }
}
