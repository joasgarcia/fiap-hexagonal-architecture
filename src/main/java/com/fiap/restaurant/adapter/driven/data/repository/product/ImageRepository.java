package com.fiap.restaurant.adapter.driven.data.repository.product;

import com.fiap.restaurant.adapter.driven.data.entity.product.ImageEntity;
import com.fiap.restaurant.adapter.driven.data.entity.product.ProductEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.product.ImageMapper;
import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.repository.product.IImageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageRepository implements IImageRepository {

    private final ImageJpaRepository imageJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public ImageRepository(ImageJpaRepository imageJpaRepository, ProductJpaRepository productJpaRepository) {
        this.imageJpaRepository = imageJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Image save(Image image) {
        ImageEntity imageEntity = ImageMapper.INSTANCE.toImageEntity(image);

        if (image.getProduct() != null) {
            ProductEntity productEntity = productJpaRepository.getReferenceById(image.getProduct().getId());
            imageEntity.setProduct(productEntity);
        }

        imageEntity = this.imageJpaRepository.save(imageEntity);
        return ImageMapper.INSTANCE.toImage(imageEntity);
    }

    @Override
    public Image update(Long id, Image image) {
        ImageEntity imageEntity = this.imageJpaRepository.getReferenceById(id);
        BeanUtils.copyProperties(image, imageEntity, "id");
        imageEntity = this.imageJpaRepository.save(imageEntity);
        return ImageMapper.INSTANCE.toImage(imageEntity);
    }

    @Override
    public void delete(Long id) {
        ImageEntity imageEntity = this.imageJpaRepository.getReferenceById(id);
        this.imageJpaRepository.delete(imageEntity);
    }

    @Override
    public List<Image> findAllByProductId(Long productId) {
        List<ImageEntity> imageEntityList = this.imageJpaRepository.findAllByProductId(productId);
        return ImageMapper.INSTANCE.toImageList(imageEntityList);
    }

    @Override
    public List<Image> findAllByItemId(Long itemId) {
        List<ImageEntity> imageEntityList = this.imageJpaRepository.findAllByItemId(itemId);
        return ImageMapper.INSTANCE.toImageList(imageEntityList);
    }
}
