package com.fiap.restaurant.cleanarchitecture.external.db.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageJpaRepository extends JpaRepository<ImageJpa, Long> {

    List<ImageJpa> findAllByProductId(Long productId);
    List<ImageJpa> findAllByItemId(Long itemId);

}
