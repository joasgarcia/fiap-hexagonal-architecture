package com.fiap.restaurant.gateway.order;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.mapper.order.ItemMapper;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

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

    @Override
    public Item getById(Long id) {
        Optional<ItemJpa> itemJpa = this.itemDatabaseConnection.getById(id);

        return ItemMapper.INSTANCE.toItem(itemJpa.get());
    }
}
