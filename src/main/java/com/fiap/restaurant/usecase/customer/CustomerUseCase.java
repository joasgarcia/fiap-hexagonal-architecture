package com.fiap.restaurant.usecase.customer;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.gateway.customer.ICustomerGateway;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;

public class CustomerUseCase {

    public static Customer save(SaveCustomerDTO saveCustomerDTO, ICustomerGateway customerGateway) {
        if (customerGateway.findByEmail(saveCustomerDTO.getEmail()) != null) throw new BusinessException("Cliente já cadastrado com o e-mail informado");
        if (customerGateway.findByCpf(saveCustomerDTO.getCpf()) != null) throw new BusinessException("Cliente já cadastrado com o CPF informado");

        Customer customer = new Customer(saveCustomerDTO.getName(), saveCustomerDTO.getEmail(), saveCustomerDTO.getCpf());
        customerGateway.save(customer);

        return customer;
    }

    public static Customer findByCpf(String cpf, ICustomerGateway customerGateway) {
        Customer customer = customerGateway.findByCpf(cpf);
        if (customer == null) throw new ResourceNotFoundException("Cliente não encontrado");

        return customer;
    }

    public static void anonymize(String cpf, ICustomerGateway customerGateway) {
        Customer customer = customerGateway.findByCpf(cpf);
        if (customer == null) throw new ResourceNotFoundException("Cliente não encontrado");

        customer.setName(DigestUtils.sha256Hex(customer.getName()));
        customer.setEmail(DigestUtils.sha256Hex(customer.getEmail()));
        customer.setCpf(DigestUtils.sha256Hex(customer.getCpf()));
        
        customerGateway.update(customer);
    }
}
