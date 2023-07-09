package com.fiap.restaurant.core.service.customer;

import com.fiap.restaurant.core.model.customer.Customer;
import com.fiap.restaurant.core.repository.customer.ICustomerRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findOrCreate(Customer customer) {
        Customer existentCustomer = this.customerRepository.findByCpfOrEmail(customer.getCpf(), customer.getEmail());
        if (existentCustomer != null) return existentCustomer;

        return this.customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return this.customerRepository.findById(id);
    }

    public Customer findByCpf(String cpf) {
        return this.customerRepository.findByCpf(cpf);
    }
}
