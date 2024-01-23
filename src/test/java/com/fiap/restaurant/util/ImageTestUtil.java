package com.fiap.restaurant.util;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.product.ImageJpa;
import com.fiap.restaurant.external.db.product.ProductJpa;

public class ImageTestUtil {

    public static ImageJpa generateJpa(ProductJpa productJpa, String src) {
        ImageJpa imageJpa = new ImageJpa();
        imageJpa.setProduct(productJpa);
        imageJpa.setSrc(src);

        return imageJpa;
    }

    public static ImageJpa generateJpa(ItemJpa itemJpa, String src) {
        ImageJpa imageJpa = new ImageJpa();
        imageJpa.setItem(itemJpa);
        imageJpa.setSrc(src);

        return imageJpa;
    }

    public static Image generateImage(Product product, String src) {
        Image image = new Image();
        image.setProduct(product);
        image.setSrc(src);

        return image;
    }

    public static Image generateImage(Item item, String src) {
        Image image = new Image();
        image.setItem(item);
        image.setSrc(src);

        return image;
    }
}
