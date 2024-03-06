package com.fiap.restaurant.gateway.customer;

import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.mapper.customer.CustomerMapper;

@SuppressWarnings("unchecked")
public class CustomerGateway implements ICustomerGateway {

    private final CustomerDatabaseConnection customerDatabaseConnection;

    public CustomerGateway(CustomerDatabaseConnection customerDatabaseConnection) {
        this.customerDatabaseConnection = customerDatabaseConnection;
    }

    @Override
    public void save(Customer customer) {
        CustomerJpa customerJpa = new CustomerJpa();
        customerJpa.setName(customer.getName());
        customerJpa.setEmail(customer.getEmail());
        customerJpa.setCpf(customer.getCpf());

        this.customerDatabaseConnection.save(customerJpa);
    }

    @Override
    public void update(Customer customer) {
        CustomerJpa customerJpa = (CustomerJpa) this.customerDatabaseConnection.getById(customer.getId());
        if (customerJpa == null) return;

        customerJpa.setName(customer.getName());
        customerJpa.setEmail(customer.getEmail());
        customerJpa.setCpf(customer.getCpf());

        this.customerDatabaseConnection.save(customerJpa);
    }

    @Override
    public Customer findByEmail(String email) {
        CustomerJpa customerJpa = (CustomerJpa) this.customerDatabaseConnection.findByEmail(email);
        if (customerJpa == null) return null;

        return CustomerMapper.INSTANCE.toCustomer(customerJpa);
    }

    @Override
    public Customer findByCpf(String cpf) {
        CustomerJpa customerJpa = (CustomerJpa) this.customerDatabaseConnection.findByCpf(cpf);
        if (customerJpa == null) return null;

        return CustomerMapper.INSTANCE.toCustomer(customerJpa);
    }
}
