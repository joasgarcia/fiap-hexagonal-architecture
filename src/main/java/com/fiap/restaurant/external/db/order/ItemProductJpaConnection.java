package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.types.interfaces.db.order.ItemProductDatabaseConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemProductJpaConnection implements ItemProductDatabaseConnection<ItemProductJpa> {

    private final CleanItemProductRepository cleanItemProductRepository;

    public ItemProductJpaConnection(CleanItemProductRepository cleanItemProductRepository) {
        this.cleanItemProductRepository = cleanItemProductRepository;
    }

    @Override
    public ItemProductJpa save(ItemProductJpa itemProduct) {
        return this.cleanItemProductRepository.save(itemProduct);
    }

    @Override
    public void delete(Long id) {
        this.cleanItemProductRepository.deleteById(id);
    }

    @Override
    public List<ItemProductJpa> findAllByItemId(Long itemId) {
        return this.cleanItemProductRepository.findAllByItemId(itemId);
    }
}
