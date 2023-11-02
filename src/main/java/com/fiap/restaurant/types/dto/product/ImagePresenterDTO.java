package com.fiap.restaurant.types.dto.product;

public class ImagePresenterDTO {

    private Long id;
    private String src;

    public ImagePresenterDTO(Long id, String src) {
        this.id = id;
        this.src = src;
    }

    public Long getId() {
        return id;
    }

    public String getSrc() {
        return src;
    }
}
