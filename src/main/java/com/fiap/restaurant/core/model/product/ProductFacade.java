package com.fiap.restaurant.core.model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductFacade extends Product {

    private List<Image> imageList;

    public ProductFacade() {
        this.imageList = new ArrayList<>();
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
