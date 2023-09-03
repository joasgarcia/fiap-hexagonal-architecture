package com.fiap.restaurant.cleanarchitecture.gateway.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.ItemProduct;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemProductJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.product.ProductJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.order.ItemProductMapper;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class ItemProductGateway implements IItemProductGateway {

    private final ItemProductDatabaseConnection itemProductDatabaseConnection;
    private final ProductDatabaseConnection productDatabaseConnection;
    private final ItemDatabaseConnection itemDatabaseConnection;

    public ItemProductGateway(ItemProductDatabaseConnection itemProductDatabaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemDatabaseConnection itemDatabaseConnection) {
        this.itemProductDatabaseConnection = itemProductDatabaseConnection;
        this.productDatabaseConnection = productDatabaseConnection;
        this.itemDatabaseConnection = itemDatabaseConnection;
    }

    @Override
    public ItemProduct save(ItemProduct itemProduct) {
        Optional<ProductJpa> productJpa = productDatabaseConnection.getById(itemProduct.getProduct().getId());
        Optional<ItemJpa> itemJpa = itemDatabaseConnection.getById(itemProduct.getItem().getId());

        ItemProductJpa itemProductJpa = new ItemProductJpa();
        itemProductJpa.setProduct(productJpa.get());
        itemProductJpa.setItem(itemJpa.get());

        itemProductJpa = (ItemProductJpa) this.itemProductDatabaseConnection.save(itemProductJpa);

        return ItemProductMapper.INSTANCE.toItemProduct(itemProductJpa);
    }

    @Override
    public void delete(ItemProduct itemProduct) {
        this.itemProductDatabaseConnection.delete(itemProduct.getId());
    }

    @Override
    public List<ItemProduct> findAllByItemId(Long itemId) {
        List<ItemProductJpa> itemProductJpaList = this.itemProductDatabaseConnection.findAllByItemId(itemId);
        return ItemProductMapper.INSTANCE.toItemProductList(itemProductJpaList);
    }
}
