package com.hit.spt.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.pojo.InventoryTransaction;
import com.hit.spt.service.GoodsService;
import com.hit.spt.service.InventoryService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    HttpServletRequest httpServletRequest;

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
    public String inventoryCheck(Model model) {
        List<Inventory> inventories = inventoryService.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        List<Inventory> inventory_lists = inventoryService.queryWarehouseList();
        model.addAttribute("inventory_lists", inventory_lists);
        return "inventoryCheck";
    }

    @RequestMapping({"addInventoryNow", "updateInventoryNow", "deleteInventoryNow"})
    public String addInventoryNow(Inventory inventory, Model model, HttpServletRequest request) {
        String uri = request.getRequestURI();
        long g_id = 0;
        int quantity_old = 0;
        int quantity_dev = 0;
        double cost_old = 0;
        double cost_new = 0;
        if (uri.charAt(1) == 'a') {
            g_id = goodsService.queryGoodsInfoByName(inventory.getName()).getG_id();
            quantity_old = inventoryService.queryQuantityByGid(g_id);
            quantity_dev = inventory.getQuantity();
            cost_old = goodsService.queryGoodsInfoByGid(g_id).getCost();
            cost_new = inventory.getCost();
            inventoryService.mergeInsertInventory(inventory);
        } else if (uri.charAt(1) == 'u') {
            Inventory inventory_old = inventoryService.queryInventoryByIId(inventory.getI_id());
            g_id = inventory_old.getG_id();
            quantity_old = inventoryService.queryQuantityByGid(g_id);
            cost_old = goodsService.queryGoodsInfoByGid(g_id).getCost();
            cost_new = cost_old;
            if (inventory.getQuantity() >= inventory_old.getQuantity()) {
                quantity_dev = -1 * inventory_old.getQuantity();
                inventoryService.deleteInventoryByIID(inventory.getI_id());
            } else {
                quantity_dev = -1 * inventory.getQuantity();
                inventory.setQuantity(inventory_old.getQuantity() - inventory.getQuantity());
                inventoryService.updateInventory(inventory);
            }
        } else if (uri.charAt(1) == 'd') {
            g_id = inventoryService.queryInventoryByIId(inventory.getI_id()).getG_id();
            quantity_old = inventoryService.queryQuantityByGid(g_id);
            quantity_dev = -1 * inventoryService.queryInventoryByIId(inventory.getI_id()).getQuantity();
            inventoryService.deleteInventoryByIID(inventory.getI_id());
        }

        // 重新计算成本价（加权平均）
        double cost_res = cost_old;
        if (quantity_dev + quantity_old != 0 && cost_new != cost_old) {
            cost_res = (cost_old * quantity_old + cost_new * quantity_dev) / (quantity_dev + quantity_old);
        } else if (quantity_dev + quantity_old == 0){
            cost_res = -1.0;
        }

        // 更新货品资料信息
        if (cost_res != cost_old) {
            GoodsInfo costUpdate = goodsService.queryGoodsInfoByGid(g_id);
            cost_res = (double) Math.round(cost_res * 100) / 100; // 保留两位小数
            costUpdate.setCost(cost_res);
            goodsService.updateCost(costUpdate);
        }

        return "redirect:inventoryView";
    }

    @RequestMapping("updateInventory")
    public String updateInventory(Integer i_id, Model model) {
        Inventory inventory = inventoryService.queryInventoryByIId(i_id);
        model.addAttribute("inventory", inventory);
        return "updateInventory";
    }

    @RequestMapping("checkInventory")
    public String checkInventory(@RequestParam("inventory") List<String> inventory, Model model) {
        if (!inventory.isEmpty()) {
            if (inventory.get(0).charAt(inventory.get(0).length() - 1) != '}') {
                for (int i = 1; i < inventory.size(); i++) {
                    inventory.set(0, inventory.get(0) + ',' + inventory.get(i));
                }
            }
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

    @RequestMapping("inventoryTrans")
    public String inventoryTrans(Model model) {
        inventoryService.refreshInventoryTransView(model, httpServletRequest);
        return "inventoryTrans";
    }

    @RequestMapping("addTransTransection")
    public String addTransTransection(Model model) {
        return null;
    }

    @RequestMapping("addOneTransformItem")
    public String addOneTransformItem(InventoryTransaction transaction, Model model) {
        inventoryService.insertInventoryTransaction(transaction);
        inventoryService.refreshInventoryTransView(model, httpServletRequest);
        return "inventoryTrans";
    }
}