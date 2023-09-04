package com.fiap.restaurant.cleanarchitecture.types.dto.order;

public class OrderScreenPresenterDTO {

    private String customerFirstName;

    private String status;

    private Long waitTimeInSeconds;

    public Long getWaitTimeInSeconds() {
        return waitTimeInSeconds;
    }

    public void setWaitTimeInSeconds(Long waitTimeInSeconds) {
        this.waitTimeInSeconds = waitTimeInSeconds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }
}
