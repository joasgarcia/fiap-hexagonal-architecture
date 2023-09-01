package com.fiap.restaurant.cleanarchitecture.types.dto.order;

import java.util.ArrayList;
import java.util.List;

public class ItemPresenterDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<ItemProductPresenterDTO> productList;
    private List<ItemImagePresenterDTO> imageList;

    public ItemPresenterDTO(Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productList = new ArrayList<>();
        this.imageList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public void addProduct(ItemProductPresenterDTO itemProductPresenterDTO) {
        this.productList.add(itemProductPresenterDTO);
    }

    public List<ItemProductPresenterDTO> getProductList() {
        return productList;
    }

    public void addImage(ItemImagePresenterDTO itemImagePresenterDTO) {
        this.imageList.add(itemImagePresenterDTO);
    }

    public List<ItemImagePresenterDTO> getImageList() {
        return imageList;
    }
}
