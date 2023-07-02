package com.fiap.restaurant.adapter.driver.api.controller.product;

import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.service.product.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
