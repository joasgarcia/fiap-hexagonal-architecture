package com.fiap.restaurant.entity.order;

import com.fiap.restaurant.entity.product.Image;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<Image> imageList;
    private List<ItemProduct> itemProductList;

    public Item(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageList = new ArrayList<>();
        this.itemProductList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void addImage(Image image) {
        this.imageList.add(image);
    }

    public List<ItemProduct> getItemProductList() {
        return itemProductList;
    }

    public void addItemProduct(ItemProduct itemProduct) {
        this.itemProductList.add(itemProduct);
    }

}
