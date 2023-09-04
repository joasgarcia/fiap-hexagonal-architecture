package com.fiap.restaurant.presenter.product;

import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.ImagePresenterDTO;

import java.util.ArrayList;
import java.util.List;

public class ImagePresenter {

    public static List<ImagePresenterDTO> fromImageList(List<Image> imageList) {
        List<ImagePresenterDTO> imagePresenterDTOList = new ArrayList<>();

        for (Image image : imageList) {
            imagePresenterDTOList.add(new ImagePresenterDTO(image.getId(), image.getSrc()));
        }

        return imagePresenterDTOList;
    }
}
