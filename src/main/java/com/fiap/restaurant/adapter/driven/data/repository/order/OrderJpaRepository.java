package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
