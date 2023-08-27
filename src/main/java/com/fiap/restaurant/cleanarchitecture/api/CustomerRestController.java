package com.fiap.restaurant.cleanarchitecture.api;

import com.fiap.restaurant.cleanarchitecture.controller.CustomerController;
import com.fiap.restaurant.cleanarchitecture.entity.Customer;
import com.fiap.restaurant.cleanarchitecture.types.dto.SaveCustomerDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.BusinessException;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.ICustomerDbConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ca/customer")
public class CustomerRestController {

    private final ICustomerDbConnection customerDbConnection;

    public CustomerRestController(ICustomerDbConnection customerDbConnection) {
        this.customerDbConnection = customerDbConnection;
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> save(@RequestBody SaveCustomerDTO saveCustomerDTO) {
        try {
            CustomerController.save(saveCustomerDTO, this.customerDbConnection);
            return ResponseEntity.ok().body(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{cpf}")
    public ResponseEntity<Customer> getByCpf(@PathVariable String cpf) {
        Customer customer = CustomerController.findByCpf(cpf, this.customerDbConnection);

        if (customer == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(customer);
    }
}
