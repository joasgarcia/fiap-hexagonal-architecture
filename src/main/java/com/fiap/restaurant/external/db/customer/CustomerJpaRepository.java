package com.fiap.restaurant.external.db.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpa, Long> {

    CustomerJpa findByEmail(String email);
    CustomerJpa findByCpf(String cpf);

}
