package com.fiap.restaurant.entity.product;

import com.fiap.restaurant.entity.order.Item;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageTest {

    @Test
    void mustInstantiateCorrectly() {
        final String src = "http://product.image.test";

        final Product product = new Product("Product 1", "Product Description 1", 5.5, "DRINK");

        Image productImage = new Image(product, null, src);
        assertThat(productImage).isNotNull();
        assertThat(productImage.getProduct().getName()).isEqualTo(product.getName());
        assertThat(productImage.getSrc()).isEqualTo(src);

        final Item item = new Item("Item 1", "Item Description 1", 17.5);

        Image itemImage = new Image(null, item, src);
        assertThat(itemImage).isNotNull();
        assertThat(itemImage.getItem().getName()).isEqualTo(item.getName());
        assertThat(itemImage.getSrc()).isEqualTo(src);
    }
}