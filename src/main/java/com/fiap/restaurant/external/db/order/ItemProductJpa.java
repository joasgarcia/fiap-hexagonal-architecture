package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.external.db.product.ProductJpa;
import jakarta.persistence.*;

@Entity(name = "item_product")
public class ItemProductJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemJpa item;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpa product;

    public Long getId() {
        return id;
    }

    public ItemJpa getItem() {
        return item;
    }

    public void setItem(ItemJpa item) {
        this.item = item;
    }

    public ProductJpa getProduct() {
        return product;
    }

    public void setProduct(ProductJpa product) {
        this.product = product;
    }
}
