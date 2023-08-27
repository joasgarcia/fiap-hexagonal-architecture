package com.fiap.restaurant.cleanarchitecture.entity.product;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String name;
    private String description;
    private Double price;
    private String category;

    private List<Image> imageList;

    public Product() {
        this.imageList = new ArrayList<>();
    }

    public void addImage(Image image) {
        this.imageList.add(image);
    }
}
