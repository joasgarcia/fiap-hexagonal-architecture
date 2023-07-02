package com.fiap.restaurant.adapter.driven.data.repository.order;

import com.fiap.restaurant.adapter.driven.data.entity.order.ItemEntity;
import com.fiap.restaurant.adapter.driven.data.mapper.order.ItemMapper;
import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.repository.order.IItemRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemRepository implements IItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    public ItemRepository(ItemJpaRepository itemJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
    }

    @Override
    public Item save(Item item) {
        ItemEntity itemEntity = ItemMapper.INSTANCE.toItemEntity(item);
        itemEntity = this.itemJpaRepository.save(itemEntity);
        return ItemMapper.INSTANCE.toItem(itemEntity);
    }
}
