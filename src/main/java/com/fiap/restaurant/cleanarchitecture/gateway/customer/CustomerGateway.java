package com.fiap.restaurant.cleanarchitecture.gateway.customer;

import com.fiap.restaurant.cleanarchitecture.external.db.customer.CustomerJpa;
import com.fiap.restaurant.cleanarchitecture.entity.customer.Customer;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer.CustomerDatabaseConnection;

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
    public Customer findByEmail(String email) {
        CustomerJpa customerJpa = (CustomerJpa) this.customerDatabaseConnection.findByEmail(email);
        if (customerJpa == null) return null;

        return new Customer(customerJpa.getName(), customerJpa.getEmail(), customerJpa.getCpf());
    }

    @Override
    public Customer findByCpf(String cpf) {
        CustomerJpa customerJpa = (CustomerJpa) this.customerDatabaseConnection.findByCpf(cpf);
        if (customerJpa == null) return null;

        return new Customer(customerJpa.getName(), customerJpa.getEmail(), customerJpa.getCpf());
    }
}
