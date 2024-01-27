package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.product.ProductJpa;

public class ProductTestUtil {

    public static ProductJpa generateJpa(String name, String description, String category, Double price) {
        ProductJpa productJpa = new ProductJpa();
        productJpa.setName(name);
        productJpa.setDescription(description);
        productJpa.setCategory(category);
        productJpa.setPrice(price);

        return productJpa;
    }

}
