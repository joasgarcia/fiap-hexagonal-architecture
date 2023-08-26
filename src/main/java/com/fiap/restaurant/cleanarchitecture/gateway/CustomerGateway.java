package com.fiap.restaurant.cleanarchitecture.gateway;

import com.fiap.restaurant.cleanarchitecture.api.db.jpa.CustomerJpa;
import com.fiap.restaurant.cleanarchitecture.entity.Customer;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.ICustomerDbConnection;

public class CustomerGateway implements ICustomerGateway {

    private final ICustomerDbConnection customerDbConnection;

    public CustomerGateway(ICustomerDbConnection customerRepository) {
        this.customerDbConnection = customerRepository;
    }

    @Override
    public void save(Customer customer) {
        CustomerJpa customerJpa = new CustomerJpa();
        customerJpa.setName(customer.getName());
        customerJpa.setEmail(customer.getEmail());
        customerJpa.setCpf(customer.getCpf());

        this.customerDbConnection.save(customerJpa);
    }

    @Override
    public Customer findByEmail(String email) {
        CustomerJpa customerJpa = this.customerDbConnection.findByEmail(email);
        if (customerJpa == null) return null;

        return new Customer(customerJpa.getName(), customerJpa.getEmail(), customerJpa.getCpf());
    }

    @Override
    public Customer findByCpf(String cpf) {
        CustomerJpa customerJpa = this.customerDbConnection.findByCpf(cpf);
        if (customerJpa == null) return null;

        return new Customer(customerJpa.getName(), customerJpa.getEmail(), customerJpa.getCpf());
    }
}
