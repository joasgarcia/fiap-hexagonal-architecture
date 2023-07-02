package com.fiap.restaurant.core.service.product;

import com.fiap.restaurant.core.mapper.product.ProductMapper;
import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.model.product.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFacadeService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    public ProductFacade save(ProductFacade productFacade) {
        Product product = ProductMapper.INSTANCE.toProduct(productFacade);
        List<Image> productImageList = productFacade.getImageList();

        product = productService.save(product);
        productFacade = ProductMapper.INSTANCE.toProductFacade(product);

        for (Image productImage : productImageList) {
            productImage.setProduct(product);
            Image image = imageService.save(productImage);

            productFacade.getImageList().add(image);
        }

        return productFacade;
    }

}
