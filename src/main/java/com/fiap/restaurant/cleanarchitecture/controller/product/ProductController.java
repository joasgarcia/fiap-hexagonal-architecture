package com.fiap.restaurant.cleanarchitecture.controller.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IProductGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.ProductGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.ProductDTO;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.usecase.product.ProductUseCase;

import java.util.List;

public class ProductController {

    public static void save(ProductDTO productDTO, ProductDatabaseConnection productDatabaseConnection) {
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        ProductUseCase.save(productDTO, productGateway);
    }

    public static List<Product> list(ProductDatabaseConnection productDatabaseConnection) {
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        return ProductUseCase.list(productGateway);
    }

    public static List<Product> findAllByCategory(String category, ProductDatabaseConnection productDatabaseConnection) {
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        return ProductUseCase.findAllByCategory(category, productGateway);
    }

    public static void delete(Long id, ProductDatabaseConnection productDatabaseConnection) {
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        ProductUseCase.delete(id, productGateway);
    }
}
