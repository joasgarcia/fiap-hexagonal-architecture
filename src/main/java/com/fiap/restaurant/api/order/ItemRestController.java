package com.fiap.restaurant.api.order;

import com.fiap.restaurant.controller.order.ItemController;
import com.fiap.restaurant.types.dto.order.ItemPresenterDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            ItemController.delete(id, this.itemDatabaseConnection, this.productDatabaseConnection, this.itemProductDatabaseConnection, this.imageDatabaseConnection);
            return ResponseEntity.ok().body(true);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
