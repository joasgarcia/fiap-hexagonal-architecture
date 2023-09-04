package com.fiap.restaurant.entity.customer;

public class Customer {

    private String name;
    private String email;
    private String cpf;

    public Customer(String name, String email, String cpf) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }
}
