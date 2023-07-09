package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.ItemProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemProductJpaRepository extends JpaRepository<ItemProductEntity, Long> {

    void deleteByProductId(Long productId);
}
