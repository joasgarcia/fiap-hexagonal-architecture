package com.fiap.restaurant.adapter.driver.api.controller.product;

import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.service.product.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/productId={productId}")
    public List<Image> findAllByProduct(@PathVariable Long productId) {
        return imageService.findAllByProductId(productId);
    }

    @GetMapping("/itemId={itemId}")
    public List<Image> findAllByItem(@PathVariable Long itemId) {
        return imageService.findAllByItemId(itemId);
    }

    @PostMapping("/")
    public Image save(@RequestBody Image image) {
        return imageService.save(image);
    }

    @PutMapping("/{id}")
    public Image update(@PathVariable Long id, @RequestBody Image image) {
        return imageService.update(id, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.imageService.delete(id);
    }
}
