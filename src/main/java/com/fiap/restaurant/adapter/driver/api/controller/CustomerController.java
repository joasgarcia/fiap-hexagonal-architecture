package com.fiap.restaurant.adapter.driver.api.controller;

import com.fiap.restaurant.core.model.Customer;
import com.fiap.restaurant.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/{cpf}")
    public ResponseEntity<Optional<Customer>> getByCpf(@PathVariable String cpf) {
        Optional<Customer> customer = customerService.findByCpf(cpf);

        if (!customer.isPresent()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return ResponseEntity.noContent().build();
    }
}
