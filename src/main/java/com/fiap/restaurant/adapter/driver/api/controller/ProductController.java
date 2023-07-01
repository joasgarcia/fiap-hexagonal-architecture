package com.fiap.restaurant.adapter.driver.api.controller;

import com.fiap.restaurant.core.model.Product;
import com.fiap.restaurant.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public List<Product> list() {
        return productService.list();
    }

    @PostMapping("/")
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@RequestBody Product product, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.productService.delete(id);
    }

}
