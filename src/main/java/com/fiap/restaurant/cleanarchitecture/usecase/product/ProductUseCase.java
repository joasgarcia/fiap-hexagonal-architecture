package com.fiap.restaurant.cleanarchitecture.usecase.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IProductGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.SaveProductDTO;
import com.fiap.restaurant.core.exception.ResourceNotFoundException;

import java.util.List;

public class ProductUseCase {

    public static Product save(SaveProductDTO saveProductDTO, IProductGateway productGateway) {
        Product product = new Product(saveProductDTO.getName(), saveProductDTO.getDescription(), saveProductDTO.getPrice(), saveProductDTO.getCategory());
        productGateway.save(product);
        return product;
    }

    public static List<Product> list(IProductGateway productGateway) {
        return productGateway.list();
    }

    public static List<Product> findAllByCategory(String category, IProductGateway productGateway) {
        return productGateway.findAllByCategory(category);
    }

    public static void delete(Long id, IProductGateway productGateway) {
        if (!productGateway.existsById(id)) throw new ResourceNotFoundException("Produto não encontrado");

        productGateway.delete(id);
    }
}
