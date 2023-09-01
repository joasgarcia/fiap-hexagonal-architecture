package com.fiap.restaurant.cleanarchitecture.api.product;

import com.fiap.restaurant.cleanarchitecture.controller.product.ProductController;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.SaveProductDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.BusinessException;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ca/product")
public class ProductRestController {

    private final ProductDatabaseConnection productDatabaseConnection;

    public ProductRestController(ProductDatabaseConnection productDatabaseConnection) {
        this.productDatabaseConnection = productDatabaseConnection;
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> save(@RequestBody SaveProductDTO saveProductDTO) {
        try {
            ProductController.save(saveProductDTO, this.productDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
