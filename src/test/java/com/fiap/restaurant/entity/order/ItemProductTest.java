package com.fiap.restaurant.entity.order;

import com.fiap.restaurant.entity.product.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemProductTest {

    @Test
    void mustInstantiateCorrectly() {
        final String itemName = "Item 1";
        final String itemDescription = "Description Item 1";
        final Double itemPrice = 10.5;
        final Item item = new Item(itemName, itemDescription, itemPrice);

        final String productName = "Product 1";
        final String productDescription = "Description Product 1";
        final Double productPrice = 12.0;
        final String productCategory = "DRINK";
        final Product product = new Product(productName, productDescription, productPrice, productCategory);

        ItemProduct itemProduct = new ItemProduct(item, product);
        assertThat(itemProduct).isNotNull();
        assertThat(itemProduct.getProduct().getName()).isEqualTo(productName);
        assertThat(itemProduct.getProduct().getDescription()).isEqualTo(productDescription);
        assertThat(itemProduct.getProduct().getPrice()).isEqualTo(productPrice);
        assertThat(itemProduct.getProduct().getCategory()).isEqualTo(productCategory);
        assertThat(itemProduct.getItem().getName()).isEqualTo(itemName);
        assertThat(itemProduct.getItem().getDescription()).isEqualTo(itemDescription);
        assertThat(itemProduct.getItem().getPrice()).isEqualTo(itemPrice);
    }
}