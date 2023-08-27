package com.fiap.restaurant.cleanarchitecture.controller.customer;

import com.fiap.restaurant.cleanarchitecture.entity.customer.Customer;
import com.fiap.restaurant.cleanarchitecture.gateway.customer.CustomerGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer.CustomerJpaRepositoryConnection;
import com.fiap.restaurant.cleanarchitecture.usecase.customer.CustomerUseCase;

public class CustomerController {

    public static void save(SaveCustomerDTO saveCustomerDTO, CustomerJpaRepositoryConnection customerJpaRepositoryConnection) {
        CustomerGateway customerGateway = new CustomerGateway(customerJpaRepositoryConnection);
        CustomerUseCase.save(saveCustomerDTO, customerGateway);
    }

    public static Customer findByCpf(String cpf, CustomerJpaRepositoryConnection customerJpaRepositoryConnection) {
        CustomerGateway customerGateway = new CustomerGateway(customerJpaRepositoryConnection);
        return CustomerUseCase.findByCpf(cpf, customerGateway);
    }
}
