package com.fiap.restaurant.entity.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    void mustInstantiateCorrectly() {
        final String name = "Product 1";
        final String description = "Description 1";
        final Double price = 4.99;
        final String category = "DRINK";

        Product product = new Product(name, description, price, category);
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getCategory()).isEqualTo(category);
    }
}