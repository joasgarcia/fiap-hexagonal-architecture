package com.fiap.restaurant.core.repository;

import com.fiap.restaurant.core.model.Item;

import java.util.List;

public interface IItemRepository {

    Item save(Item item);
    Item update(Item item);
    void delete(Long id);
    List<Item> list();
}
