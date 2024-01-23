package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;

public class CustomerTestUtil {

    public static CustomerJpa generateJpa(String name, String email, String cpf) {
        CustomerJpa customerJpa = new CustomerJpa();
        customerJpa.setName(name);
        customerJpa.setEmail(email);
        customerJpa.setCpf(cpf);

        return customerJpa;
    }

    public static SaveCustomerDTO generateSaveCustomerDTO(String name, String email, String cpf) {
        SaveCustomerDTO saveCustomerDTO = new SaveCustomerDTO();
        saveCustomerDTO.setName(name);
        saveCustomerDTO.setEmail(email);
        saveCustomerDTO.setCpf(cpf);

        return saveCustomerDTO;
    }
}
