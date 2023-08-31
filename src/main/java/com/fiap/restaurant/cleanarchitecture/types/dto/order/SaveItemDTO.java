package com.fiap.restaurant.cleanarchitecture.types.dto.order;

import com.fiap.restaurant.cleanarchitecture.types.dto.IdDTO;

import java.util.ArrayList;
import java.util.List;

public class SaveItemDTO {

    private String name;
    private String description;
    private Double price;
    private List<String> imageSrcList;
    private List<IdDTO> productIdList;

    public SaveItemDTO() {
        this.imageSrcList = new ArrayList<>();
        this.productIdList = new ArrayList<>();
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

    public List<String> getImageSrcList() {
        return imageSrcList;
    }

    public List<IdDTO> getProductIdList() {
        return productIdList;
    }
}
