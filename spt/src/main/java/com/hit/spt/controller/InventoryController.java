package com.hit.spt.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.service.GoodsService;
import com.hit.spt.service.InventoryService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @Autowired
    GoodsService goodsService;

    /**
     * 添加库存实体类
     *
     * @return 进行网页转发，转发至addInventory
     */
    @RequestMapping("addInventory")
    public String addInventory(Model model) {
        List<GoodsInfo> goodsInfos = goodsService.getAllGoods();
        model.addAttribute("goodsInfos", goodsInfos);
        List<Inventory> inventory_lists = inventoryService.queryWarehouseList();
        model.addAttribute("inventory_lists", inventory_lists);
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

    @RequestMapping("inventoryCheck")
    public String inventoryCheck(Model model){
        List<Inventory> inventories = inventoryService.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        List<Inventory> inventory_lists = inventoryService.queryWarehouseList();
        model.addAttribute("inventory_lists", inventory_lists);
        return "inventoryCheck";
    }

    @RequestMapping({"addInventoryNow", "updateInventoryNow", "deleteInventoryNow"})
    public String addInventoryNow(Inventory inventory, Model model, HttpServletRequest request){
        String uri = request.getRequestURI();
        if(uri.charAt(1) == 'a') {
            List<Inventory> inventory1 = inventoryService.selectInventoryByName(inventory.getName());
            if(! inventory1.isEmpty() && inventory1.get(0).getI_id().equals(inventory.getI_id())){
                inventory.setI_id(inventory1.get(0).getI_id());
                inventoryService.mergeInventory(inventory);
            }else {
                inventoryService.insertInventoryWithGoodName(inventory);
            }
        }else if(uri.charAt(1) == 'u'){
            if (inventory != null)
            inventoryService.updateInventory(inventory);
        }else if(uri.charAt(1) == 'd'){
            inventoryService.deleteInventoryByIID(inventory.getI_id());
        }
        List<Inventory> inventories = inventoryService.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        return "inventoryView";
    }

    @RequestMapping("updateInventory")
    public String updateInventory(Integer i_id, Model model){
        Inventory inventory = inventoryService.queryInventoryById(i_id);
        model.addAttribute("inventory", inventory);
        return "updateInventory";
    }

    @RequestMapping("checkInventory")
    public String checkInventory(@RequestParam("inventory") List<String> inventory, Model model){
        if(! inventory.isEmpty()) {
            if (inventory.get(0).charAt(inventory.get(0).length() - 1) != '}') {
                for (int i = 1; i < inventory.size(); i++) {
                    inventory.set(0, inventory.get(0) + ',' + inventory.get(i));
                }
            }
            inventory = inventory.subList(0, 1);
            for (String inv : inventory) {
                JSONObject jbo = JSONObject.parseObject(inv);
                Inventory inventory1 = new Inventory();
                inventory1.setI_id(jbo.getInteger("i_id"));
                inventory1.setG_id(jbo.getLong("g_id"));
                inventory1.setQuality(jbo.getString("quality"));
                inventory1.setQuantity(jbo.getInteger("quantity"));
                inventoryService.updateInventory(inventory1);
            }
        }
        List<Inventory> inventories = inventoryService.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        return "inventoryCheck";
    }
}