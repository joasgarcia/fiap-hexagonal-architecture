package com.fiap.restaurant.cleanarchitecture.usecase.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IProductGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.SaveProductDTO;

public class ProductUseCase {

    public static Product save(SaveProductDTO saveProductDTO, IProductGateway productGateway) {
        Product product = new Product(saveProductDTO.getName(), saveProductDTO.getDescription(), saveProductDTO.getPrice(), saveProductDTO.getCategory());
        productGateway.save(product);
        return product;
    }
}
