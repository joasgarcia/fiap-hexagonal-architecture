package com.fiap.restaurant.adapter.driver.api.controller.order.dto;

public class OrderAddItemDTO {

    Long itemId;

    String observation;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
