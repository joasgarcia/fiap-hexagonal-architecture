package com.fiap.restaurant.adapter.driver.api.controller.customer;

import com.fiap.restaurant.core.model.customer.Customer;
import com.fiap.restaurant.core.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/{cpf}")
    public ResponseEntity<Customer> getByCpf(@PathVariable String cpf) {
        Customer customer = customerService.findByCpf(cpf);

        if (customer == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(customer);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        customer = this.customerService.save(customer);

        if (customer == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(customer);
    }
}
