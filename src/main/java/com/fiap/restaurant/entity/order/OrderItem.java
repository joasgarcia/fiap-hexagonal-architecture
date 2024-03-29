package com.fiap.restaurant.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderItem {

    @JsonIgnore
    private Order order;

    private Item item;

    private String observation;

    public OrderItem() {}

    public OrderItem(Order order, Item item, String observation) {
        this.order = order;
        this.item = item;
        this.observation = observation;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}
