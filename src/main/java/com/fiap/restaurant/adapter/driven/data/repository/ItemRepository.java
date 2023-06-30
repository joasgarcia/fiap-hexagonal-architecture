package com.fiap.restaurant.adapter.driven.data.repository;

import com.fiap.restaurant.adapter.driven.data.entity.ItemEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.ItemMapper;
import com.fiap.restaurant.core.model.Item;
import com.fiap.restaurant.core.repository.IItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemRepository implements IItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    public ItemRepository(ItemJpaRepository itemJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
    }

    @Override
    public Item save(Item item) {
        ItemEntity itemEntity = ItemMapper.toItemEntity(item);
        itemEntity = this.itemJpaRepository.save(itemEntity);
        return ItemMapper.toItem(itemEntity);
    }

    @Override
    public Item update(Item item) {
        return null;
    }

    @Override
    public void delete(Long id) {
        ItemEntity itemEntity = this.itemJpaRepository.getReferenceById(id);
        this.itemJpaRepository.delete(itemEntity);
    }

    @Override
    public List<Item> list() {
        List<ItemEntity> list = this.itemJpaRepository.findAll();
        return ItemMapper.toItemList(list);
    }
}
