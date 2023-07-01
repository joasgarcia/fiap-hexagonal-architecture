package com.fiap.restaurant.adapter.driven.data.entity.order;


import com.fiap.restaurant.adapter.driven.data.entity.product.ProductEntity;
import jakarta.persistence.*;

@Entity(name = "item_product")
public class ItemProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
