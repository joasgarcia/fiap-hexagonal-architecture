package com.fiap.restaurant.adapter.driven.data.entity.order;

import jakarta.persistence.*;

@Entity(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    public OrderEntity getOrder() {
        return order;
    }

    public ItemEntity getItem() {
        return item;
    }
}
