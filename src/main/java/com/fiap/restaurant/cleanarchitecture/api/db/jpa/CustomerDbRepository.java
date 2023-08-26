package com.fiap.restaurant.cleanarchitecture.api.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDbRepository extends JpaRepository<CustomerJpa, Long> {

    CustomerJpa findByEmail(String email);
    CustomerJpa findByCpf(String cpf);

}
