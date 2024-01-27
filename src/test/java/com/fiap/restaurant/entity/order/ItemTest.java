package com.fiap.restaurant.entity.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    void mustInstantiateCorrectly() {
        final String name = "Item 1";
        final String description = "Description Item 1";
        final Double price = 10.5;
        final Item item = new Item(name, description, price);
        assertThat(item).isNotNull();
        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getDescription()).isEqualTo(description);
        assertThat(item.getPrice()).isEqualTo(price);
    }
}
