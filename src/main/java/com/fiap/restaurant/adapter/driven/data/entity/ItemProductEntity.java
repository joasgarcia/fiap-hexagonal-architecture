package com.fiap.restaurant.adapter.driven.data.entity;


import jakarta.persistence.*;

@Entity(name = "item_product")
public class ItemProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id"/*, table = "item", nullable = false*/)
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "product_id"/*, table = "product", nullable = false*/)
    private ProductEntity product;
}
