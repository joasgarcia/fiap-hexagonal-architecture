package com.fiap.restaurant.controller.product;

import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.gateway.product.IProductGateway;
import com.fiap.restaurant.gateway.product.ProductGateway;
import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.usecase.product.ProductUseCase;

import java.util.List;

public class ProductController {

    public static Product save(ProductDTO productDTO, ProductDatabaseConnection productDatabaseConnection) {
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        return ProductUseCase.save(productDTO, productGateway);
    }

    public static Product update(Long id, ProductDTO productDTO, ProductDatabaseConnection productDatabaseConnection) {
        IProductGateway productGateway = new ProductGateway(productDatabaseConnection);
        return ProductUseCase.update(id, productDTO, productGateway);
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
