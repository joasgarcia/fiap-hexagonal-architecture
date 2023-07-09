package com.fiap.restaurant.core.service.product;

import com.fiap.restaurant.core.mapper.product.ProductMapper;
import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.model.order.ItemProduct;
import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.model.product.ProductFacade;
import com.fiap.restaurant.core.service.order.ItemProductService;
import com.fiap.restaurant.core.service.order.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ProductFacadeService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemProductService itemProductService;

    public ProductFacade save(ProductFacade productFacade) {
        Product product = ProductMapper.INSTANCE.toProduct(productFacade);
        List<Image> productImageList = productFacade.getImageList();

        product = productService.save(product);
        productFacade = ProductMapper.INSTANCE.toProductFacade(product);

        Item item = new Item();
        item.setName(product.getName());
        item.setPrice(product.getPrice());
        item = this.itemService.save(item);

        ItemProduct itemProduct = new ItemProduct();
        itemProduct.setProduct(product);
        itemProduct.setItem(item);
        itemProductService.save(itemProduct);

        for (Image productImage : productImageList) {
            productImage.setProduct(product);
            Image image = imageService.save(productImage);

            productFacade.getImageList().add(image);
        }

        return productFacade;
    }

}
