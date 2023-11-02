package com.fiap.restaurant.cleanarchitecture.api.product;

import com.fiap.restaurant.cleanarchitecture.controller.product.ProductController;
import com.fiap.restaurant.cleanarchitecture.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.ProductDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.BusinessException;
import com.fiap.restaurant.cleanarchitecture.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    private final ProductDatabaseConnection productDatabaseConnection;

    public ProductRestController(ProductDatabaseConnection productDatabaseConnection) {
        this.productDatabaseConnection = productDatabaseConnection;
    }

    @GetMapping("/")
    public List<Product> list() {
        return ProductController.list(this.productDatabaseConnection);
    }

    @GetMapping("/listByCategory/{category}")
    public List<Product> listByCategory(@PathVariable("category") String category) {
        return ProductController.findAllByCategory(category, this.productDatabaseConnection);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> save(@RequestBody ProductDTO productDTO) {
        try {
            Product product = ProductController.save(productDTO, this.productDatabaseConnection);
            return ResponseEntity.ok().body(product);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product product = ProductController.update(id, productDTO, this.productDatabaseConnection);
            return ResponseEntity.ok().body(product);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            ProductController.delete(id, this.productDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
