package com.hit.spt.controller;

import com.hit.spt.pojo.Inventory;
import com.hit.spt.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @RequestMapping("inventory/add")
    public String addInventory(Inventory inventory) {
        inventoryService.insertInventoryChange(inventory);
        return "addInventory";
    }

    @RequestMapping("inventoryView")
    public String inventoryView(Model model){
        List<Inventory> inventories = inventoryService.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        return "inventoryView";
    }
}










