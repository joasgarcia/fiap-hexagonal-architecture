package com.fiap.restaurant.cleanarchitecture.api.order;

import com.fiap.restaurant.cleanarchitecture.controller.order.ItemController;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.ItemPresenterDTO;
import com.fiap.restaurant.cleanarchitecture.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ca/item")
public class ItemRestController {

    private final ItemDatabaseConnection itemDatabaseConnection;
    private final ProductDatabaseConnection productDatabaseConnection;
    private final ItemProductDatabaseConnection itemProductDatabaseConnection;
    private final ImageDatabaseConnection imageDatabaseConnection;

    public ItemRestController(ItemDatabaseConnection itemDatabaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemProductDatabaseConnection itemProductDatabaseConnection, ImageDatabaseConnection imageDatabaseConnection) {
        this.itemDatabaseConnection = itemDatabaseConnection;
        this.productDatabaseConnection = productDatabaseConnection;
        this.itemProductDatabaseConnection = itemProductDatabaseConnection;
        this.imageDatabaseConnection = imageDatabaseConnection;
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> save(@RequestBody SaveItemDTO saveItemDTO) {
        try {
            ItemPresenterDTO itemPresenterDTO = ItemController.save(saveItemDTO, this.itemDatabaseConnection, this.productDatabaseConnection, this.itemProductDatabaseConnection, this.imageDatabaseConnection);
            return ResponseEntity.ok(itemPresenterDTO);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
