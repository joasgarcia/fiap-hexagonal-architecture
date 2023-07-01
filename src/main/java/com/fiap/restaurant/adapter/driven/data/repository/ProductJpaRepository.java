package com.fiap.restaurant.adapter.driven.data.repository;

import com.fiap.restaurant.adapter.driven.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
