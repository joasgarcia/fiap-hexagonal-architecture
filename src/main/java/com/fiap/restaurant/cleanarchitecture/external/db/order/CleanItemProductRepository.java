package com.fiap.restaurant.cleanarchitecture.external.db.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanItemProductRepository extends JpaRepository<ItemProductJpa, Long> {
}
