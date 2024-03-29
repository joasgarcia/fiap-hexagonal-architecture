package com.fiap.restaurant.controller.order;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.gateway.order.IItemGateway;
import com.fiap.restaurant.gateway.order.IItemProductGateway;
import com.fiap.restaurant.gateway.order.ItemGateway;
import com.fiap.restaurant.gateway.order.ItemProductGateway;
import com.fiap.restaurant.gateway.product.IImageGateway;
import com.fiap.restaurant.gateway.product.IProductGateway;
import com.fiap.restaurant.gateway.product.ImageGateway;
import com.fiap.restaurant.gateway.product.ProductGateway;
import com.fiap.restaurant.presenter.order.ItemPresenter;
import com.fiap.restaurant.types.dto.order.ItemPresenterDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.usecase.order.ItemUseCase;

public class ItemController {

    public static ItemPresenterDTO save(SaveItemDTO saveItemDTO, ItemDatabaseConnection itemDatabaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemProductDatabaseConnection itemProductDatabaseConnection, ImageDatabaseConnection imageDatabaseConnection) {
        IItemGateway itemGateway = new ItemGateway(itemDatabaseConnection);
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        IItemProductGateway itemProductGateway = new ItemProductGateway(itemProductDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
        IImageGateway imageGateway = new ImageGateway(imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);

        Item item = ItemUseCase.save(saveItemDTO, itemGateway, productGateway, itemProductGateway, imageGateway);
        return ItemPresenter.fromItem(item);
    }

    public static void delete(Long id, ItemDatabaseConnection itemDatabaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemProductDatabaseConnection itemProductDatabaseConnection, ImageDatabaseConnection imageDatabaseConnection) {
        IItemGateway itemGateway = new ItemGateway(itemDatabaseConnection);
        IItemProductGateway itemProductGateway = new ItemProductGateway(itemProductDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
        IImageGateway imageGateway = new ImageGateway(imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);

        ItemUseCase.delete(id, itemGateway, itemProductGateway, imageGateway);
    }
}
