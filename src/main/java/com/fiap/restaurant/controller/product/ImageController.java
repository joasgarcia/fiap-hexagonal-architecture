package com.fiap.restaurant.controller.product;

import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.gateway.product.IImageGateway;
import com.fiap.restaurant.gateway.product.IProductGateway;
import com.fiap.restaurant.gateway.product.ImageGateway;
import com.fiap.restaurant.gateway.product.ProductGateway;
import com.fiap.restaurant.presenter.product.ImagePresenter;
import com.fiap.restaurant.types.dto.product.ImagePresenterDTO;
import com.fiap.restaurant.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.types.dto.product.UpdateImageDTO;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.usecase.product.ImageUseCase;

import java.util.List;

public class ImageController {

    public static Image saveProductImage(SaveProductImageDTO saveProductImageDTO, ImageDatabaseConnection imageDatabaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemDatabaseConnection itemDatabaseConnection) {
        IImageGateway imageGateway = new ImageGateway(imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        return ImageUseCase.saveProductImage(saveProductImageDTO, imageGateway, productGateway);
    }

    public static Image update(Long id, UpdateImageDTO updateImageDTO, ImageDatabaseConnection imageDataBaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemDatabaseConnection itemDatabaseConnection) {
        IImageGateway imageGateway = new ImageGateway(imageDataBaseConnection, productDatabaseConnection, itemDatabaseConnection);
        return ImageUseCase.update(id, updateImageDTO, imageGateway);
    }

    public static void delete(Long id, ImageDatabaseConnection imageDatabaseConnection) {
        IImageGateway imageGateway = new ImageGateway(imageDatabaseConnection);
        ImageUseCase.delete(id, imageGateway);
    }

    public static List<ImagePresenterDTO> findAllByProductId(Long productId, ImageDatabaseConnection imageDatabaseConnection) {
        IImageGateway imageGateway = new ImageGateway(imageDatabaseConnection);
        List<Image> imageList = ImageUseCase.findAllByProduct(productId, imageGateway);
        return ImagePresenter.fromImageList(imageList);
    }
}
