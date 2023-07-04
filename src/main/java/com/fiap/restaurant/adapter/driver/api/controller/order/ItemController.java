package com.fiap.restaurant.adapter.driver.api.controller.order;

import com.fiap.restaurant.core.model.order.ItemFacade;
import com.fiap.restaurant.core.service.order.ItemFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemFacadeService itemFacadeService;

    @PostMapping("/")
    public ItemFacade save(@RequestBody ItemFacade itemFacade) {
        return this.itemFacadeService.save(itemFacade);
    }

}
