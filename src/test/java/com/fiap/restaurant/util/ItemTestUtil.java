package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.order.ItemJpa;

public class ItemTestUtil {

    public static ItemJpa generateJpa(String name, String description, Double price) {
        ItemJpa itemJpa = new ItemJpa();
        itemJpa.setName(name);
        itemJpa.setDescription(description);
        itemJpa.setPrice(price);

        return itemJpa;
    }

}
