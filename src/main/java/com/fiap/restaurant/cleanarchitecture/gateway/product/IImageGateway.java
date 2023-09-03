package com.fiap.restaurant.cleanarchitecture.gateway.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Image;

import java.util.List;

public interface IImageGateway {

    Image save(Image image);
    Image update(Image image);
    Image getById(Long id);
    boolean existsById(Long id);
    void delete(Long id);
    List<Image> findAllByProduct(Long productId);
    List<Image> findAllByItemId(Long itemId);

}
