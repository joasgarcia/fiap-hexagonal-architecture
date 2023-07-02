package com.fiap.restaurant.core.model.order;

import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ItemFacade extends Item {

    private List<Image> imageList;
    private List<Product> productList;

    public ItemFacade() {
        this.productList = new ArrayList<>();
        this.imageList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
