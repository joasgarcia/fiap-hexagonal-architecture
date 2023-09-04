package com.fiap.restaurant.types.mapper.order;

import com.fiap.restaurant.entity.order.ItemProduct;
import com.fiap.restaurant.external.db.order.ItemProductJpa;
import com.fiap.restaurant.types.mapper.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemProductMapper {

    ItemProductMapper INSTANCE = Mappers.getMapper(ItemProductMapper.class);

    default ItemProduct toItemProduct(ItemProductJpa itemProductJpa) {
        ItemProduct itemProduct = new ItemProduct();
        itemProduct.setId((itemProductJpa.getId()));
        itemProduct.setProduct(ProductMapper.INSTANCE.toProduct(itemProductJpa.getProduct()));
        itemProduct.setItem(ItemMapper.INSTANCE.toItem(itemProductJpa.getItem()));

        return itemProduct;
    }

    default List<ItemProduct> toItemProductList(List<ItemProductJpa> itemProductJpaList) {
        List<ItemProduct> itemProductList = new ArrayList<>();

        for (ItemProductJpa itemProductJpa : itemProductJpaList) {
            itemProductList.add(toItemProduct(itemProductJpa));
        }

        return itemProductList;
    }
}
