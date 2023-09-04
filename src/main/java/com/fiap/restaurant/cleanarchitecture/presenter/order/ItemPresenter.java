package com.fiap.restaurant.cleanarchitecture.presenter.order;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.entity.order.ItemProduct;
import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.ItemImagePresenterDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.ItemPresenterDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.ItemProductPresenterDTO;

public class ItemPresenter {

    public static ItemPresenterDTO fromItem(Item item) {
        ItemPresenterDTO itemPresenterDTO = new ItemPresenterDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice());

        for (ItemProduct itemProduct : item.getItemProductList()) {
            Product product = itemProduct.getProduct();
            ItemProductPresenterDTO itemProductPresenterDTO = new ItemProductPresenterDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory());
            itemPresenterDTO.addProduct(itemProductPresenterDTO);
        }

        for (Image image : item.getImageList()) {
            ItemImagePresenterDTO itemImagePresenterDTO = new ItemImagePresenterDTO(image.getId(), image.getSrc());
            itemPresenterDTO.addImage(itemImagePresenterDTO);
        }

        return itemPresenterDTO;
    }

}
