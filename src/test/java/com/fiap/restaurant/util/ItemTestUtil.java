package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;

public class ItemTestUtil {

    public static ItemJpa generateJpa(String name, String description, Double price) {
        ItemJpa itemJpa = new ItemJpa();
        itemJpa.setName(name);
        itemJpa.setDescription(description);
        itemJpa.setPrice(price);

        return itemJpa;
    }

    public static SaveItemDTO generateSaveItemDTO(String name, String description, Double price) {
        SaveItemDTO saveItemDTO = new SaveItemDTO();
        saveItemDTO.setName(name);
        saveItemDTO.setDescription(description);
        saveItemDTO.setPrice(price);

        return saveItemDTO;
    }

}
