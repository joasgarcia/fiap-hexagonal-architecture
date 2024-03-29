package com.fiap.restaurant.api.customer;

import com.fiap.restaurant.controller.customer.CustomerController;
import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
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

    @GetMapping(path = "/{cpf}")
    public ResponseEntity<Object> getByCpf(@PathVariable String cpf) {
        try {
            Customer customer = CustomerController.findByCpf(cpf, this.customerDatabaseConnection);
            return ResponseEntity.ok().body(customer);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/anonymize/{cpf}")
    public ResponseEntity<Object> anonymize(@PathVariable String cpf) {
        try {
            CustomerController.anonymize(cpf, this.customerDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
