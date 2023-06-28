package com.fiap.restaurant.adapter.driven.data.repository;

import com.fiap.restaurant.adapter.driven.data.entity.CustomerEntity;
import com.fiap.restaurant.core.repository.ICustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, ICustomerRepository {
}
