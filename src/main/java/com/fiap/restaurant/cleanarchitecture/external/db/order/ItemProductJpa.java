package com.fiap.restaurant.cleanarchitecture.external.db.order;

import com.fiap.restaurant.cleanarchitecture.external.db.product.ProductJpa;
import jakarta.persistence.*;

@Entity(name = "item_product_ca")
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

    public void setItem(ItemJpa item) {
        this.item = item;
    }

    public void setProduct(ProductJpa product) {
        this.product = product;
    }
}
