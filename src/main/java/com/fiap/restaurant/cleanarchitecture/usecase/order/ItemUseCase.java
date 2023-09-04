package com.fiap.restaurant.cleanarchitecture.usecase.order;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.entity.order.ItemProduct;
import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.cleanarchitecture.gateway.order.IItemGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.order.IItemProductGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IImageGateway;
import com.fiap.restaurant.cleanarchitecture.gateway.product.IProductGateway;
import com.fiap.restaurant.cleanarchitecture.types.dto.IdDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.product.ImageSrcDTO;
import com.fiap.restaurant.cleanarchitecture.types.exception.ResourceNotFoundException;

import java.util.List;

public class ItemUseCase {

    public static Item save(SaveItemDTO saveItemDTO, IItemGateway itemGateway, IProductGateway productGateway, IItemProductGateway itemProductGateway, IImageGateway imageGateway) {
        if (saveItemDTO.getProductIdList().isEmpty()) throw new ResourceNotFoundException("O(s) produto(s) do item não informado(s)");

        Item item = new Item(saveItemDTO.getName(), saveItemDTO.getDescription(), saveItemDTO.getPrice());
        item = itemGateway.save(item);

        for (IdDTO productId : saveItemDTO.getProductIdList()) {
            Product product = productGateway.getById(productId.getId());

            ItemProduct itemProduct = new ItemProduct();
            itemProduct.setProduct(product);
            itemProduct.setItem(item);
            itemProduct = itemProductGateway.save(itemProduct);

            item.addItemProduct(itemProduct);
        }

        for (ImageSrcDTO imageSrc : saveItemDTO.getImageSrcList()) {
            Image image = new Image();
            image.setItem(item);
            image.setSrc(imageSrc.getSrc());
            image = imageGateway.save(image);

            item.addImage(image);
        }

        return item;
    }

    public static void delete(Long id, IItemGateway itemGateway, IItemProductGateway itemProductGateway, IImageGateway imageGateway) {
        if (!itemGateway.existsById(id)) throw new ResourceNotFoundException("Item não encontrado");

        List<ItemProduct> itemProductList = itemProductGateway.findAllByItemId(id);

        for (ItemProduct itemProduct : itemProductList) {
            itemProductGateway.delete(itemProduct);
        }

        List<Image> imageList = imageGateway.findAllByItemId(id);

        for (Image image : imageList) {
            imageGateway.delete(image.getId());
        }

        itemGateway.delete(id);
    }
}
