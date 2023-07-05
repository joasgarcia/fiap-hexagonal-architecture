package com.fiap.restaurant.core.service.product;

import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.repository.product.IImageRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class ImageService {

    private final IImageRepository imageRepository;

    public ImageService(IImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image save(Image image) {
        return this.imageRepository.save(image);
    }

    public Image update(Long id, Image image) {
        return this.imageRepository.update(id, image);
    }

    public void delete(Long id) {
        this.imageRepository.delete(id);
    }

    public List<Image> findAllByProductId(Long productId) {
        return this.imageRepository.findAllByProductId(productId);
    }

    public List<Image> findAllByItemId(Long itemId) {
        return this.imageRepository.findAllByItemId(itemId);
    }
}
