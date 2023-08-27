package com.fiap.restaurant.cleanarchitecture.api.customer;

import com.fiap.restaurant.cleanarchitecture.controller.customer.CustomerController;
import com.fiap.restaurant.cleanarchitecture.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.BusinessException;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer.CustomerDatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ca/customer")
public class CustomerRestController {

    private final CustomerDatabaseConnection customerDatabaseConnection;

    public CustomerRestController(CustomerDatabaseConnection customerDatabaseConnection) {
        this.customerDatabaseConnection = customerDatabaseConnection;
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> save(@RequestBody SaveCustomerDTO saveCustomerDTO) {
        try {
            CustomerController.save(saveCustomerDTO, this.customerDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
