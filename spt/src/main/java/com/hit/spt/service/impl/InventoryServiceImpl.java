package com.hit.spt.service.impl;

import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.mapper.InventoryMapper;
import com.hit.spt.mapper.InventoryTransactionMapper;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.pojo.InventoryTransaction;
import com.hit.spt.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    InventoryTransactionMapper inventoryTransactionMapper;

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @Override
    public int insertInventoryChange(Inventory inventory) {
        assert inventoryMapper != null;
        return inventoryMapper.insertInventory(inventory);
    }

    /**
     * @return
     */
    @Override
    public List<Inventory> queryInventoryList() {
        return inventoryMapper.queryInventoryList();
    }

    @Override
    public List<Inventory> queryInventoryWithGnameList() {
        return inventoryMapper.queryInventoryWithGnameList();
    }

    @Override
    public Integer updateInventory(Inventory inventory){
        if(inventory.getQuantity() > 0) {
            return inventoryMapper.updateInventory(inventory);
        }else{
            return deleteInventoryByIID(inventory.getI_id());
        }
    }

    @Override
    public Inventory queryInventoryById(Integer i_id){
        return inventoryMapper.queryInventoryById(i_id);
    }

    @Override
    public Integer insertInventoryWithGoodName(Inventory inventory){
        if(inventory.getQuantity() >=0)
            return inventoryMapper.insertInventoryWithGoodName(inventory);
        else return -1;
    }

    @Override
    public List<Inventory> selectInventoryByName(String name){
        return inventoryMapper.selectInventoryByName(name);
    }

    @Override
    public Integer mergeInventory(Inventory inventory){
        return inventoryMapper.mergeInventory(inventory);
    }
    @Override
    public Integer mergeInsertInventory(Inventory inventory){
        List<Inventory> inventory1 = this.selectInventoryByName(inventory.getName());
        if((!inventory1.isEmpty()) && inventory1.get(0).getIl_id().equals(inventory.getIl_id())){
            inventory.setIl_id(inventory1.get(0).getIl_id());
            return this.mergeInventory(inventory);
        }else {
            return this.insertInventoryWithGoodName(inventory);
        }
    }
    @Override
    public Integer deleteInventoryByIID(Integer i_id){
        return inventoryMapper.deleteInventoryByIId(i_id);
    }

    @Override
    public List<Inventory> queryWarehouseList(){
        return inventoryMapper.queryWarehouseList();
    }

    @Override
    public Inventory queryInventoryByIdAndIlID(Integer g_id, Integer il_id){
        return  inventoryMapper.queryInventoryByIdAndIlID(g_id, il_id);
    }
    @Override
    public List<InventoryTransaction> getInventoryTransactionWithUid(Integer u_id){
        List<InventoryTransaction> transactions = inventoryTransactionMapper.getInventoryTransaction(u_id);
        int transactions_len = transactions.size();
        for(int i = 0;i < transactions_len;i ++){
            InventoryTransaction inventoryTransaction = transactions.get(i);
            Inventory s_inventory = inventoryMapper.queryInventoryById(inventoryTransaction.getI_id_s());
            String goods_name = s_inventory.getName();
            inventoryTransaction.setS_quantity(s_inventory.getQuantity());
            String s_inventory_name = s_inventory.getInventory_name();
            Inventory warehouse = inventoryMapper.queryWarehouseByIlID(inventoryTransaction.getIl_id_d());
            String d_inventory_name = warehouse.getInventory_name();
            inventoryTransaction.setD_inventory_name(d_inventory_name);
            inventoryTransaction.setS_inventory_name(s_inventory_name);
            inventoryTransaction.setGoods_name(goods_name);
        }
        return transactions;
    }

    @Override
    public Integer insertInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return inventoryTransactionMapper.insertInventoryTransaction(inventoryTransaction);
    }

    @Override
    public void refreshInventoryTransView(Model model, HttpServletRequest httpServletRequest) {
        List<Inventory> inventories = this.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        List<Inventory> inventory_lists = this.queryWarehouseList();
        model.addAttribute("inventory_lists", inventory_lists);
        Integer u_id = (Integer) httpServletRequest.getSession().getAttribute("u_id");
        List<InventoryTransaction> inventoryTransactions = this.getInventoryTransactionWithUid(u_id);
        model.addAttribute("inventory_transactions", inventoryTransactions);
    }
}
