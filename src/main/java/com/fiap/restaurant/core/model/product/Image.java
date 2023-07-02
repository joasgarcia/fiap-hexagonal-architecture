package com.fiap.restaurant.core.model.product;

import com.fiap.restaurant.core.model.order.Item;

public class Image {

    private String src;
    private Product product;
    private Item item;

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
