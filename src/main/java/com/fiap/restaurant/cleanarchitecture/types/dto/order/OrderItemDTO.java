package com.fiap.restaurant.cleanarchitecture.types.dto.order;

public class OrderItemDTO {

    private Long itemId;

    private String observation;

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
