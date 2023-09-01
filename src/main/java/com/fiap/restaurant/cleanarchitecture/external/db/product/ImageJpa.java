package com.fiap.restaurant.cleanarchitecture.external.db.product;

import com.fiap.restaurant.adapter.driven.data.entity.order.ItemEntity;
import jakarta.persistence.*;

@Entity(name = "image_ca")
public class ImageJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "src", nullable = false)
    private String src;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpa product;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

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

    public ProductJpa getProduct() {
        return product;
    }

    public void setProduct(ProductJpa product) {
        this.product = product;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

}
