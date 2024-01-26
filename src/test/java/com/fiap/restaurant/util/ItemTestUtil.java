package com.fiap.restaurant.util;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.types.dto.IdDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.dto.product.ImageSrcDTO;

import java.util.ArrayList;
import java.util.List;

public class ItemTestUtil {

    public static ItemJpa generateJpa(String name, String description, Double price) {
        ItemJpa itemJpa = new ItemJpa();
        itemJpa.setName(name);
        itemJpa.setDescription(description);
        itemJpa.setPrice(price);

        return itemJpa;
    }

    public static SaveItemDTO generateSaveItemDTO(String name, String description, Double price) {
        return generateSaveItemDTO(name, description, price, new ArrayList<>(), new ArrayList<>());
    }

    public static SaveItemDTO generateSaveItemDTO(String name, String description, Double price, List<IdDTO> productIdList, List<ImageSrcDTO> imageSrcList) {
        SaveItemDTO saveItemDTO = new SaveItemDTO();
        saveItemDTO.setName(name);
        saveItemDTO.setDescription(description);
        saveItemDTO.setPrice(price);
        saveItemDTO.setImageSrcList(imageSrcList);
        saveItemDTO.setProductIdList(productIdList);

        return saveItemDTO;
    }

}
