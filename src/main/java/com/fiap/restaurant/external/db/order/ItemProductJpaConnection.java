package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.types.interfaces.db.order.ItemProductDatabaseConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemProductJpaConnection implements ItemProductDatabaseConnection<ItemProductJpa> {

    private final ItemProductRepository itemProductRepository;

    public ItemProductJpaConnection(ItemProductRepository itemProductRepository) {
        this.itemProductRepository = itemProductRepository;
    }

    @Override
    public ItemProductJpa save(ItemProductJpa itemProduct) {
        return this.itemProductRepository.save(itemProduct);
    }

    @Override
    public void delete(Long id) {
        this.itemProductRepository.deleteById(id);
    }

    @Override
    public List<ItemProductJpa> findAllByItemId(Long itemId) {
        return this.itemProductRepository.findAllByItemId(itemId);
    }
}
