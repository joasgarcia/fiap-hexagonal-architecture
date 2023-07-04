package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.core.exception.ResourceNotFoundException;
import com.fiap.restaurant.core.mapper.order.ItemMapper;
import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.model.order.ItemFacade;
import com.fiap.restaurant.core.model.order.ItemProduct;
import com.fiap.restaurant.core.model.product.Image;
import com.fiap.restaurant.core.model.product.Product;
import com.fiap.restaurant.core.service.product.ImageService;
import com.fiap.restaurant.core.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ItemFacadeService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ItemProductService itemProductService;

    @Autowired
    private ImageService imageService;

    public ItemFacade save(ItemFacade itemFacade) {
        Item item = ItemMapper.INSTANCE.toItem(itemFacade);
        List<Image> itemImageList = itemFacade.getImageList();
        List<Product> productList = itemFacade.getProductList();
        if (productList.isEmpty()) throw new ResourceNotFoundException("Produto(s) não informado(s)");

        item = itemService.save(item);
        itemFacade = ItemMapper.INSTANCE.toItemFacade(item);

        for (Product product : productList) {
            ItemProduct itemProduct = new ItemProduct();
            itemProduct.setItem(item);
            itemProduct.setProduct(product);

            itemProduct = itemProductService.save(itemProduct);

            itemFacade.getProductList().add(itemProduct.getProduct());
        }

        for (Image imageImage : itemImageList) {
            imageImage.setItem(item);
            Image image = imageService.save(imageImage);

            itemFacade.getImageList().add(image);
        }

        return itemFacade;
    }
}
