package com.fiap.restaurant.external.db.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpa, Long> {

    @Query(value = """
        SELECT *
        FROM order_ca orderca
        WHERE status != 'FINISHED'
        ORDER BY CASE orderca.status
                     WHEN 'READY' THEN 0
                     WHEN 'PREPARING' THEN 1
                     WHEN 'RECEIVED' THEN 2
                     ELSE 4 END ASC, id asc
            """, nativeQuery = true)
    List<OrderJpa> listOrderedByStatusAndId();
}
