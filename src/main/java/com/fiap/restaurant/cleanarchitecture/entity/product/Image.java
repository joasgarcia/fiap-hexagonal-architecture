package com.fiap.restaurant.cleanarchitecture.entity.product;

import com.fiap.restaurant.cleanarchitecture.entity.order.Item;

public class Image {

    private Long id;
    private String src;
    private Product product;
    private Item item;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
