package com.hit.spt.controller;

import com.hit.spt.pojo.Inventory;
import com.hit.spt.service.AddInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class InventoryController {
    @Autowired
    AddInventoryService addInventoryService;

    @RequestMapping("inventory/add")
    public String addInventory(Inventory inventory) {
        addInventoryService.insertInventoryChange(inventory);
        return "addInventory";
    }
}
