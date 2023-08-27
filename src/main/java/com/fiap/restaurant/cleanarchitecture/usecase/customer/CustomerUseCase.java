package com.fiap.restaurant.cleanarchitecture.usecase.customer;

import com.fiap.restaurant.cleanarchitecture.entity.Customer;
import com.fiap.restaurant.cleanarchitecture.gateway.ICustomerGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.SaveCustomerDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.BusinessException;

public class CustomerUseCase {

    public static Customer save(SaveCustomerDTO saveCustomerDTO, ICustomerGateway customerGateway) {
        if (customerGateway.findByEmail(saveCustomerDTO.getEmail()) != null) throw new BusinessException("Cliente já cadastrado com o e-mail informado");
        if (CustomerUseCase.findByCpf(saveCustomerDTO.getCpf(), customerGateway) != null) throw new BusinessException("Cliente já cadastrado com o CPF informado");

        Customer customer = new Customer(saveCustomerDTO.getName(), saveCustomerDTO.getEmail(), saveCustomerDTO.getCpf());
        customerGateway.save(customer);

        return customer;
    }

    public static Customer findByCpf(String cpf, ICustomerGateway customerGateway) {
        return customerGateway.findByCpf(cpf);
    }
}
