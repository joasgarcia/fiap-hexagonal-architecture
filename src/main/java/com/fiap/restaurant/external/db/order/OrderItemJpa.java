package com.fiap.restaurant.external.db.order;

import jakarta.persistence.*;

@Entity(name = "order_item")
public class OrderItemJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderJpa order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemJpa item;

    @Column(name = "observation")
    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderJpa getOrder() {
        return order;
    }

    public void setOrder(OrderJpa order) {
        this.order = order;
    }

    public ItemJpa getItem() {
        return item;
    }

    public void setItem(ItemJpa item) {
        this.item = item;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
