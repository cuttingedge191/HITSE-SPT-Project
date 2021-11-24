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

    /**
     * 添加库存实体类
     *
     * @param inventory 库存实体类，参照sql中的结构
     * @return 进行网页转发，转发至addInventory
     */
    @RequestMapping("inventory/add")
    public String addInventory(Inventory inventory) {
        inventoryService.insertInventoryChange(inventory);
        return "addInventory";
    }

    /**
     * 查看库存信息
     *
     * @param model 是视图，可以添加属性，传递信息
     * @return 转发至inventoryView
     */
    @RequestMapping("inventoryView")
    public String inventoryView(Model model) {
        List<Inventory> inventories = inventoryService.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        return "inventoryView";
    }
}










