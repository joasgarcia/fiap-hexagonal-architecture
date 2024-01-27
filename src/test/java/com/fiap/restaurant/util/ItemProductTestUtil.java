package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.ItemProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpa;

public class ItemProductTestUtil {

    public static ItemProductJpa generateJpa(ItemJpa itemJpa, ProductJpa productJpa) {
        ItemProductJpa itemProductJpa = new ItemProductJpa();
        itemProductJpa.setProduct(productJpa);
        itemProductJpa.setItem(itemJpa);

        return itemProductJpa;
    }

}
