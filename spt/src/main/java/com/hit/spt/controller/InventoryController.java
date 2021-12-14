package com.hit.spt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.pojo.InventoryTransaction;
import com.hit.spt.service.GoodsService;
import com.hit.spt.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
        inventoryService.updateInventoryView(model);
        return "inventoryView";
    }

    @RequestMapping("inventoryCheck")
    public String inventoryCheck(Model model) {
        List<Inventory> inventories = inventoryService.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
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
            if (inventory != null) {
                g_id = goodsService.queryGoodsInfoByName(inventory.getName()).getG_id();
                quantity_old = inventoryService.queryQuantityByGid(g_id);
                quantity_dev = -1 * inventory.getQuantity();
                cost_old = goodsService.queryGoodsInfoByGid(g_id).getCost();
                cost_new = cost_old;
                Inventory inventory_old = inventoryService.queryInventoryByIId(inventory.getI_id());
                Integer decreaseQuantity = inventory.getQuantity();
                inventory_old.setQuality(inventory.getQuality());
                inventoryService.decreaseInventory(decreaseQuantity, inventory_old);
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
        } else if (quantity_dev + quantity_old == 0) {
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

    @RequestMapping("changePrior")
    public String changePrior(@RequestParam("inventory") List<String> inventory, Model model){
        if(!inventory.isEmpty()){
            inventory = safeExtractJSONStrings(inventory);
            for(String inv:inventory){
                JSONObject jbo = JSON.parseObject(inv);
                Inventory inventory1 = new Inventory();
                inventory1.setInventory_info(jbo.getString("inventory_info"));
                inventory1.setInventory_name(jbo.getString("inventory_name"));
                inventory1.setInventory_prior(jbo.getInteger("inventory_prior"));
                inventory1.setIl_id(jbo.getInteger("il_id"));
                inventoryService.updateWarehouse(inventory1);
            }
        }
        return "redirect:inventoryView";
    }

    private List<String> safeExtractJSONStrings(List<String> inventory) {
        if(inventory.get(0).charAt(inventory.get(0).length() - 1) != '}'){
            for(int i = 1; i < inventory.size(); i ++){
                inventory.set(0, inventory.get(0) + ',' + inventory.get(i));
            }
            inventory = inventory.subList(0, 1);
        }
        return inventory;
    }

    @RequestMapping("checkInventory")
    public String checkInventory(@RequestParam("inventory") List<String> inventory, Model model) {
        if (!inventory.isEmpty()) {
            inventory = safeExtractJSONStrings(inventory);
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

        model.addAttribute("warn", true);
        inventoryService.refreshInventoryTransView(model, httpServletRequest);
        return "inventoryTrans";
    }

    @RequestMapping("addTransTransaction")
    public String addTransTransaction(InventoryTransaction transaction, Model model) {
        Inventory inventory = inventoryService.queryInventoryByIId(transaction.getI_id_s());
        transaction.setG_id(inventory.getG_id());
        transaction.setIl_id_s(inventory.getIl_id());
        inventoryService.insertInventoryTransaction(transaction);


        // 修改库存
        Integer decreaseInteger = transaction.getQuantity();
        inventoryService.decreaseInventory(decreaseInteger, inventory);
        inventory.setIl_id(transaction.getIl_id_d());
        inventory.setQuantity(transaction.getQuantity());
        inventoryService.mergeInsertInventory(inventory);

        model.addAttribute("warn", true);
        inventoryService.refreshInventoryTransView(model, httpServletRequest);
        return "inventoryTrans";
    }


//    @RequestMapping("deleteInventoryTransaction")
//    public String deleteInventoryTransaction(Integer iti_id, Model model) {
//        boolean success = inventoryService.deleteInventoryTransactionByItiId(iti_id);
//        if(success) {
//            model.addAttribute("warn", "false");
//        }else{
//            model.addAttribute("warn", "true");
//        }
//        inventoryService.refreshInventoryTransView(model, httpServletRequest);
//        return "inventoryTrans";
//    }

    @RequestMapping("retreatInventoryTrans")
    public String retreatInventoryTrans(Integer iti_id, Model model) {
        boolean success = inventoryService.deleteInventoryTransactionByItiId(iti_id);
        model.addAttribute("warn", success);
        inventoryService.refreshInventoryTransView(model, httpServletRequest);
        return "inventoryTrans";
    }

    @RequestMapping("sureToTransInventory")
    public String retreatInventoryTrans(Model model) {
        Integer u_id = (Integer) httpServletRequest.getSession().getAttribute("u_id");
        inventoryService.deleteInventoryTransactionByUId(u_id);
        inventoryService.refreshInventoryTransView(model, httpServletRequest);

        model.addAttribute("warn", true);
        return "inventoryTrans";
    }

    @RequestMapping("addInventoryList")
    public String addInventoryList(Inventory inventory, Model model){
        List<Inventory> inventories = inventoryService.queryWarehouseList();
        Integer maxPrior = 0;
        for(Inventory warehouse:inventories){
            maxPrior = Math.max(maxPrior, warehouse.getInventory_prior());
        }
        inventory.setInventory_prior(maxPrior + 1);
        inventoryService.insertWarehouse(inventory);
        return "redirect:inventoryView";
    }
}
