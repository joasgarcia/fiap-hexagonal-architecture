package com.fiap.restaurant.util;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.product.ImageJpa;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.types.dto.product.UpdateImageDTO;

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

    public static SaveProductImageDTO generateSaveProductImageDTO(Long productId, String src) {
        SaveProductImageDTO saveProductImageDTO = new SaveProductImageDTO();
        saveProductImageDTO.setProductId(productId);
        saveProductImageDTO.setSrc(src);

        return saveProductImageDTO;
    }

    public static UpdateImageDTO generateUpdateImageDTO(String src) {
        UpdateImageDTO updateImageDTO = new UpdateImageDTO();
        updateImageDTO.setSrc(src);

        return updateImageDTO;
    }
}
