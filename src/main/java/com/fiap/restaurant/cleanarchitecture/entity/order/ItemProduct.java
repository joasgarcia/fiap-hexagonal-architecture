package com.fiap.restaurant.cleanarchitecture.entity.order;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;

public class ItemProduct {

    private Long id;

    private Item item;
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
