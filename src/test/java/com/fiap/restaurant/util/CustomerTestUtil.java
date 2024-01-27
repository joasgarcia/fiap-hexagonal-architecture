package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;

import java.util.Random;

public class CustomerTestUtil {

    public static final String CPF = "71841727016";

    public static String randomCpf() {
        String cpf = "";
        Random random = new Random();

        for (int i = 0; i < 11; i++) {
            cpf = cpf.concat(String.valueOf(random.nextInt(0,9)));
        }

        return cpf;
    }

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
