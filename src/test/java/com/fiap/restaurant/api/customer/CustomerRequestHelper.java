package com.fiap.restaurant.api.customer;

import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;

import java.util.UUID;

public class CustomerRequestHelper {

    public static SaveCustomerDTO buildSaveRequest() {
        String randomUuid = UUID.randomUUID().toString();

        SaveCustomerDTO saveCustomerDTO = new SaveCustomerDTO();
        saveCustomerDTO.setName("John Doe");
        saveCustomerDTO.setEmail(randomUuid + "@email.com");
        saveCustomerDTO.setCpf(randomUuid);

        return saveCustomerDTO;
    }
}
