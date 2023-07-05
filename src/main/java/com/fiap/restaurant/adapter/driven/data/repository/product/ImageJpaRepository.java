package com.fiap.restaurant.adapter.driven.data.repository.product;

import com.fiap.restaurant.adapter.driven.data.entity.product.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findAllByProductId(Long productId);
    List<ImageEntity> findAllByItemId(Long itemId);
}