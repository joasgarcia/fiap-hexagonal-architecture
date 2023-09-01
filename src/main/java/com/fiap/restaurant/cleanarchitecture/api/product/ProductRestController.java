package com.fiap.restaurant.cleanarchitecture.api.product;

import com.fiap.restaurant.cleanarchitecture.controller.product.ProductController;
import com.fiap.restaurant.cleanarchitecture.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.SaveProductDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.BusinessException;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ca/product")
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
    public ResponseEntity<Object> save(@RequestBody SaveProductDTO saveProductDTO) {
        try {
            ProductController.save(saveProductDTO, this.productDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (BusinessException businessException) {
            return new ResponseEntity<>(businessException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
