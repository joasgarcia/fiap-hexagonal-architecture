package com.fiap.restaurant.types.mapper.product;

import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.external.db.product.ImageJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    Image toImage(ImageJpa imageJpa);

    default List<Image> toImageList(List<ImageJpa> imageJpaList) {
        List<Image> imageList = new ArrayList<>();

        for (ImageJpa imageJpa : imageJpaList) {
            imageList.add(toImage(imageJpa));
        }

        return imageList;
    }
}
