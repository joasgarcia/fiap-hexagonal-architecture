package com.fiap.restaurant.cleanarchitecture.controller;

import com.fiap.restaurant.cleanarchitecture.entity.Customer;
import com.fiap.restaurant.cleanarchitecture.gateway.CustomerGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.SaveCustomerDTO;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.ICustomerDbConnection;
import com.fiap.restaurant.cleanarchitecture.usecase.customer.CustomerUseCase;

public class CustomerController {

    public static void save(SaveCustomerDTO saveCustomerDTO, ICustomerDbConnection customerRepository) {
        CustomerGateway customerGateway = new CustomerGateway(customerRepository);
        CustomerUseCase.save(saveCustomerDTO, customerGateway);
    }

    public static Customer findByCpf(String cpf, ICustomerDbConnection customerRepository) {
        CustomerGateway customerGateway = new CustomerGateway(customerRepository);
        return CustomerUseCase.findByCpf(cpf, customerGateway);
    }
}
