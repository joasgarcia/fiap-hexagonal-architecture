package com.fiap.restaurant.types.dto.order;

import com.fiap.restaurant.types.dto.IdDTO;
import com.fiap.restaurant.types.dto.product.ImageSrcDTO;

import java.util.ArrayList;
import java.util.List;

public class SaveItemDTO {

    private String name;
    private String description;
    private Double price;
    private List<ImageSrcDTO> imageSrcList;
    private List<IdDTO> productIdList;

    public SaveItemDTO() {
        this.imageSrcList = new ArrayList<>();
        this.productIdList = new ArrayList<>();
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

    public List<ImageSrcDTO> getImageSrcList() {
        return imageSrcList;
    }

    public List<IdDTO> getProductIdList() {
        return productIdList;
    }
}
