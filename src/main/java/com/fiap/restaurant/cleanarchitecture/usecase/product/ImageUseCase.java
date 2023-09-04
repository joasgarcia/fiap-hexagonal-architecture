package com.fiap.restaurant.cleanarchitecture.usecase.product;

import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IImageGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IProductGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.UpdateImageDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.ResourceNotFoundException;

import java.util.List;

public class ImageUseCase {

    public static Image saveProductImage(SaveProductImageDTO saveProductImageDTO, IImageGateway imageGateway, IProductGateway productGateway) {
        Product product = productGateway.getById(saveProductImageDTO.getProductId());
        if (product == null) throw new ResourceNotFoundException("Produto não encontrado.");

        Image image = new Image();
        image.setSrc(saveProductImageDTO.getSrc());
        image.setProduct(product);

        return imageGateway.save(image);
    }

    public static Image update(Long id, UpdateImageDTO updateImageDTO, IImageGateway imageGateway) {
        if (!imageGateway.existsById(id)) throw new ResourceNotFoundException("Imagem não encontrada");

        Image image = imageGateway.getById(id);
        image.setSrc(updateImageDTO.getSrc());
        return imageGateway.update(image);
    }

    public static void delete(Long id, IImageGateway imageGateway) {
        if (!imageGateway.existsById(id)) throw new ResourceNotFoundException("Imagem não encontrada");

        imageGateway.delete(id);
    }

    public static List<Image> findAllByProduct(Long productId, IImageGateway imageGateway) {
        return imageGateway.findAllByProduct(productId);
    }
}
