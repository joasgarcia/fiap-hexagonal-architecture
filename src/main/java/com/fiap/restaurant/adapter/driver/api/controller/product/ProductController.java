package com.fiap.restaurant.adapter.driver.api.controller.product;

import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.model.product.ProductFacade;
import com.fiap.restaurant.core.service.product.ProductFacadeService;
import com.fiap.restaurant.core.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductFacadeService productFacadeService;

    @GetMapping("/")
    public List<Product> list() {
        return productService.list();
    }

    @PostMapping("/")
    public ProductFacade save(@RequestBody ProductFacade productFacade) {
        return this.productFacadeService.save(productFacade);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return this.productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.productService.delete(id);
    }

}
