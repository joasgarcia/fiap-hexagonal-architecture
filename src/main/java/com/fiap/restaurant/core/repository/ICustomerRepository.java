package com.fiap.restaurant.core.repository;

import com.fiap.restaurant.adapter.driven.data.entity.CustomerEntity;

import java.util.Optional;

public interface ICustomerRepository {

    Optional<CustomerEntity> findByCpf(String cpf);
}
