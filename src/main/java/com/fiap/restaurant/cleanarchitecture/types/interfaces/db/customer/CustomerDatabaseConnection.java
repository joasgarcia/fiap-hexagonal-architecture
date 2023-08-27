package com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer;


public interface CustomerDatabaseConnection<T> {

    void save(T customerJpa);

    T findByEmail(String email);

    T findByCpf(String cpf);
}
