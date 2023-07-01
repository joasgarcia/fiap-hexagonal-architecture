package com.fiap.restaurant.adapter.driven.data.entity.product;

import com.fiap.restaurant.adapter.driven.data.entity.order.ItemEntity;
import jakarta.persistence.*;

@Entity(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "src", nullable = false)
    private String src;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
}
