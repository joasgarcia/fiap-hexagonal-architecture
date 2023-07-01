package com.fiap.restaurant.adapter.driven.data.repository.customer;

import com.fiap.restaurant.adapter.driven.data.entity.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

    CustomerEntity findByCpf(String cpf);
}
