package com.fiap.restaurant.cleanarchitecture.external.db.product;

import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ImageDatabaseConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageJpaConnection implements ImageDatabaseConnection<ImageJpa> {

    private final CleanImageJpaRepository imageJpaRepository;

    public ImageJpaConnection(CleanImageJpaRepository imageJpaRepository) {
        this.imageJpaRepository = imageJpaRepository;
    }

    @Override
    public ImageJpa save(ImageJpa image) {
        return this.imageJpaRepository.save(image);
    }

    @Override
    public void delete(ImageJpa image) {
        this.imageJpaRepository.delete(image);
    }

    @Override
    public ImageJpa getById(Long id) {
        return this.imageJpaRepository.getReferenceById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.imageJpaRepository.existsById(id);
    }

    @Override
    public List<ImageJpa> findAllByProductId(Long productId) {
        return this.imageJpaRepository.findAllByProductId(productId);
    }

    @Override
    public List<ImageJpa> findAllByItemId(Long itemId) {
        return this.imageJpaRepository.findAllByItemId(itemId);
    }
}
