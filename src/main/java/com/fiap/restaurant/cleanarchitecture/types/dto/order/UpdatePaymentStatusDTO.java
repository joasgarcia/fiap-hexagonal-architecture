package com.fiap.restaurant.cleanarchitecture.types.dto.order;

public class UpdatePaymentStatusDTO {

    private String paymentStatus;

    private Long externalIdentifier;

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(Long externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }
}
