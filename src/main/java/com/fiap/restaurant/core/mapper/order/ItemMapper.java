package com.fiap.restaurant.core.mapper.order;

import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.model.order.ItemFacade;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toItem(ItemFacade itemFacade);

    ItemFacade toItemFacade(Item item);
}
