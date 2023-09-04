package com.fiap.restaurant.types.dto.order;

public class ItemImagePresenterDTO {

    private Long id;
    private String src;

    public ItemImagePresenterDTO(Long id, String src) {
        this.id = id;
        this.src = src;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
