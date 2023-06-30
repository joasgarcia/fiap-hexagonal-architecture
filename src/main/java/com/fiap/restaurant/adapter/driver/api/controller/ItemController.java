package com.fiap.restaurant.adapter.driver.api.controller;

import com.fiap.restaurant.core.model.Item;
import com.fiap.restaurant.core.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public List<Item> list() {
        return itemService.list();
    }

    @PostMapping("/")
    public Item save(@RequestBody Item item) {
        return this.itemService.save(item);
    }

    @PutMapping("/{id}")
    public Item update(@RequestBody Item item, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.itemService.delete(id);
    }

}
