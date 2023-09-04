package com.fiap.restaurant.cleanarchitecture.gateway.order;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.order.ItemMapper;
import org.springframework.beans.BeanUtils;

@SuppressWarnings("unchecked")
public class ItemGateway implements IItemGateway {

    private final ItemDatabaseConnection itemDatabaseConnection;

    public ItemGateway(ItemDatabaseConnection itemDatabaseConnection) {
        this.itemDatabaseConnection = itemDatabaseConnection;
    }

    @Override
    public Item save(Item item) {
        ItemJpa itemJpa = new ItemJpa();
        BeanUtils.copyProperties(item, itemJpa, "id");

        itemJpa = (ItemJpa) this.itemDatabaseConnection.save(itemJpa);

        return ItemMapper.INSTANCE.toItem(itemJpa);
    }

    @Override
    public void delete(Long id) {
        this.itemDatabaseConnection.delete(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.itemDatabaseConnection.existsById(id);
    }
}
