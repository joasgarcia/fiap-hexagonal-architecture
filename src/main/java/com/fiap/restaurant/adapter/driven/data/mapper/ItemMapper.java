package com.fiap.restaurant.adapter.driven.data.mapper;

import com.fiap.restaurant.adapter.driven.data.entity.ItemEntity;
import com.fiap.restaurant.core.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public static Item toItem(ItemEntity itemEntity) {
        Item item = new Item();
        item.setId(itemEntity.getId());
        item.setName(itemEntity.getName());

        return item;
    }

    public static ItemEntity toItemEntity(Item item) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName(item.getName());

        return itemEntity;
    }

    public static List<Item> toItemList(List<ItemEntity> itemEntityList) {
        List<Item> itemList = new ArrayList<>();

        for (ItemEntity itemEntity : itemEntityList) {
            itemList.add(toItem(itemEntity));
        }

        return itemList;
    }
}
