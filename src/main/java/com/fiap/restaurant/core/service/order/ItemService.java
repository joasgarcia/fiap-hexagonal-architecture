package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.repository.order.IItemRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ItemService {

    private final IItemRepository itemRepository;

    public ItemService(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        return this.itemRepository.save(item);
    }

    public Item findById(Long id) {
        return this.itemRepository.findById(id);
    }
}
