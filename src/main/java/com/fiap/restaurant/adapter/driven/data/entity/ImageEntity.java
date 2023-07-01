package com.fiap.restaurant.adapter.driven.data.entity;

import jakarta.persistence.*;

@Entity(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "src", nullable = false)
    private String src;

    @ManyToOne
    @JoinColumn(name = "product_id"/*, table = "product"*/)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "item_id"/*, table = "item"*/)
    private ItemEntity item;
}
