package com.fiap.restaurant.cleanarchitecture.types.dto.product;

public class SaveProductImageDTO {

    private String src;
    private Long productId;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
