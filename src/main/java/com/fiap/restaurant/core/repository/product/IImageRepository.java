package com.fiap.restaurant.core.repository.product;

import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.model.product.Product;

import java.util.List;

public interface IImageRepository {

    Image save(Image image);
    Image update(Long id, Image image);
    void delete(Long id);
    List<Image> findAllByProductId(Long productId);

}
