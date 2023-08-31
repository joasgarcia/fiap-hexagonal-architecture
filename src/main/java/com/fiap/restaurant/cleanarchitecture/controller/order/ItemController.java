package com.fiap.restaurant.cleanarchitecture.controller.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.Item;
import com.fiap.restaurant.cleanarchitecture.gateway.order.IItemGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.order.IItemProductGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.order.ItemGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.order.ItemProductGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IImageGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IProductGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.ImageGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.ProductGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.usecase.order.ItemUseCase;

public class ItemController {

    public static Item save(SaveItemDTO saveItemDTO, ItemDatabaseConnection itemDatabaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemProductDatabaseConnection itemProductDatabaseConnection, ImageDatabaseConnection imageDatabaseConnection) {
        IItemGateway itemGateway = new ItemGateway(itemDatabaseConnection);
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        IItemProductGateway itemProductGateway = new ItemProductGateway(itemProductDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
        IImageGateway imageGateway = new ImageGateway(imageDatabaseConnection);

        return ItemUseCase.save(saveItemDTO, itemGateway, productGateway, itemProductGateway, imageGateway);
    }
}
