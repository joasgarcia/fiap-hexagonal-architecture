package com.fiap.restaurant.cleanarchitecture.gateway.customer;

import com.fiap.restaurant.cleanarchitecture.external.db.customer.CustomerJpa;
import com.fiap.restaurant.cleanarchitecture.entity.customer.Customer;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer.CustomerJpaRepositoryConnection;

public class CustomerGateway implements ICustomerGateway {

    private final CustomerJpaRepositoryConnection customerJpaRepositoryConnection;

    public CustomerGateway(CustomerJpaRepositoryConnection customerJpaRepositoryConnection) {
        this.customerJpaRepositoryConnection = customerJpaRepositoryConnection;
    }

    @Override
    public void save(Customer customer) {
        CustomerJpa customerJpa = new CustomerJpa();
        customerJpa.setName(customer.getName());
        customerJpa.setEmail(customer.getEmail());
        customerJpa.setCpf(customer.getCpf());

        this.customerJpaRepositoryConnection.save(customerJpa);
    }

    @Override
    public Customer findByEmail(String email) {
        CustomerJpa customerJpa = this.customerJpaRepositoryConnection.findByEmail(email);
        if (customerJpa == null) return null;

        return new Customer(customerJpa.getName(), customerJpa.getEmail(), customerJpa.getCpf());
    }

    @Override
    public Customer findByCpf(String cpf) {
        CustomerJpa customerJpa = this.customerJpaRepositoryConnection.findByCpf(cpf);
        if (customerJpa == null) return null;

        return new Customer(customerJpa.getName(), customerJpa.getEmail(), customerJpa.getCpf());
    }
}
