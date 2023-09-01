package com.fiap.restaurant.cleanarchitecture.controller.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Image;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IImageGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IProductGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.ImageGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.ProductGateway;
import com.fiap.restaurant.cleanarchitecture.presenter.product.ImagePresenter;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.ImagePresenterDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.UpdateImageDTO;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.usecase.product.ImageUseCase;

import java.util.List;

public class ImageController {

    public static Image saveProductImage(SaveProductImageDTO saveProductImageDTO, ImageDatabaseConnection imageDatabaseConnection, ProductDatabaseConnection productDatabaseConnection) {
        IImageGateway imageGateway = new ImageGateway(imageDatabaseConnection, productDatabaseConnection);
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        return ImageUseCase.saveProductImage(saveProductImageDTO, imageGateway, productGateway);
    }

    public static Image update(Long id, UpdateImageDTO updateImageDTO, ImageDatabaseConnection imageDataBaseConnection, ProductDatabaseConnection productDatabaseConnection) {
        IImageGateway imageGateway = new ImageGateway(imageDataBaseConnection, productDatabaseConnection);
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
