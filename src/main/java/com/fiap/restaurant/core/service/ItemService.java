package com.fiap.restaurant.core.service;

import com.fiap.restaurant.core.model.Item;
import com.fiap.restaurant.core.repository.IItemRepository;

import java.util.List;

public class ItemService {

    private final IItemRepository itemRepository;

    public ItemService(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        return this.itemRepository.save(item);
    }

    public Item update(Item item) {
        return this.itemRepository.update(item);
    }

    public void delete(Long id) {
        this.itemRepository.delete(id);
    }

    public List<Item> list() {
        return this.itemRepository.list();
    }

}
