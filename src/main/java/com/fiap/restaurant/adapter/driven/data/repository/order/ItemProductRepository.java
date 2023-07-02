package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.ItemEntity;
import com.fiap.restaurant.adapter.driven.data.entity.order.ItemProductEntity;
import com.fiap.restaurant.adapter.driven.data.entity.product.ProductEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.order.ItemProductMapper;
import com.fiap.restaurant.adapter.driven.data.repository.product.ProductJpaRepository;
import com.fiap.restaurant.core.model.order.ItemProduct;
import com.fiap.restaurant.core.repository.order.IItemProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemProductRepository implements IItemProductRepository {

    private final ItemProductJpaRepository itemProductJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public ItemProductRepository(ItemProductJpaRepository itemProductJpaRepository, ItemJpaRepository itemJpaRepository, ProductJpaRepository productJpaRepository) {
        this.itemProductJpaRepository = itemProductJpaRepository;
        this.itemJpaRepository = itemJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public ItemProduct save(ItemProduct itemProduct) {
        ItemProductEntity itemProductEntity = ItemProductMapper.INSTANCE.toItemProductEntity(itemProduct);

        ProductEntity productEntity = productJpaRepository.getReferenceById(itemProduct.getProduct().getId());
        itemProductEntity.setProduct(productEntity);

        ItemEntity itemEntity = itemJpaRepository.getReferenceById(itemProduct.getItem().getId());
        itemProductEntity.setItem(itemEntity);

        itemProductEntity = this.itemProductJpaRepository.save(itemProductEntity);

        return ItemProductMapper.INSTANCE.toItemProduct(itemProductEntity);
    }
}
