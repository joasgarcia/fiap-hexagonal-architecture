package com.fiap.restaurant.adapter.driven.data.mapper.product;

import com.fiap.restaurant.adapter.driven.data.entity.product.ImageEntity;
import com.fiap.restaurant.core.model.product.Image;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    default Image toImage(ImageEntity imageEntity) {
        if (imageEntity == null) return null;

        Image image = new Image();
        image.setSrc(imageEntity.getSrc());
        return image;
    }
    default ImageEntity toImageEntity(Image image) {
        if (image == null) return null;

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setSrc(image.getSrc());
        return imageEntity;
    }

    default List<Image> toImageList(List<ImageEntity> imageEntityList) {
        List<Image> imageList = new ArrayList<>();
        imageEntityList.forEach(imageEntity -> imageList.add(toImage(imageEntity)));
        return imageList;
    }

}
