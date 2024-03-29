package com.fiap.restaurant.types.interfaces.db.customer;


public interface CustomerDatabaseConnection<T> {

    void save(T customerJpa);

    T getById(Long id);

    T findByEmail(String email);

    T findByCpf(String cpf);
}
