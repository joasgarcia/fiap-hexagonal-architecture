package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.types.dto.product.ProductDTO;

public class ProductTestUtil {

    public static ProductJpa generateJpa(String name, String description, String category, Double price) {
        ProductJpa productJpa = new ProductJpa();
        productJpa.setName(name);
        productJpa.setDescription(description);
        productJpa.setCategory(category);
        productJpa.setPrice(price);

        return productJpa;
    }

    public static ProductDTO generateDTO(String name, String description, String category, Double price) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setCategory(category);
        productDTO.setPrice(price);

        return productDTO;
    }

}
