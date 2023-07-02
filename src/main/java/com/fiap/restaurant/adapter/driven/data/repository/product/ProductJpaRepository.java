package com.fiap.restaurant.adapter.driven.data.repository.product;

import com.fiap.restaurant.adapter.driven.data.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    public List<ProductEntity> findAllByCategory(String category);
}
