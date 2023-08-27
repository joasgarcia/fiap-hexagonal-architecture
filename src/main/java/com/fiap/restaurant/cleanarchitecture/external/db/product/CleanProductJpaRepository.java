package com.fiap.restaurant.cleanarchitecture.external.db.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanProductJpaRepository extends JpaRepository<ProductJpa, Long> {

}
