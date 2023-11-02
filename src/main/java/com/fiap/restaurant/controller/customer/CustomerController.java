package com.fiap.restaurant.controller.customer;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.gateway.customer.CustomerGateway;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.usecase.customer.CustomerUseCase;

public class CustomerController {

    public static void save(SaveCustomerDTO saveCustomerDTO, CustomerDatabaseConnection customerDatabaseConnection) {
        CustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
        CustomerUseCase.save(saveCustomerDTO, customerGateway);
    }

    public static Customer findByCpf(String cpf, CustomerDatabaseConnection customerDatabaseConnection) {
        CustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
        return CustomerUseCase.findByCpf(cpf, customerGateway);
    }
}
