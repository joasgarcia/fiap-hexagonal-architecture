package com.fiap.restaurant.external.db.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemProductRepository extends JpaRepository<ItemProductJpa, Long> {

    List<ItemProductJpa> findAllByItemId(Long itemId);
}
