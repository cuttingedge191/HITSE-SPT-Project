package com.hit.spt.service.impl;

import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.mapper.InventoryMapper;
import com.hit.spt.mapper.InventoryTransactionMapper;
import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.pojo.InventoryTransaction;
import com.hit.spt.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @Autowired
    InventoryTransactionMapper inventoryTransactionMapper;


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
    public Inventory queryInventoryByIId(Integer i_id){
        return inventoryMapper.queryInventoryById(i_id);
    }

    @Override
    public Integer queryQuantityByGid(Long g_id) {
        List<Integer> quantities = inventoryMapper.queryQuantityByGid(g_id);
        int sum = 0;
        if (quantities == null)
            return 0;
        for (Integer i : quantities)
            sum += i;
        return sum;
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
        int i = 0;
        Integer inventory1_len = inventory1.size();
        for(;i<inventory1_len;i++){
            if(inventory1.get(i).getIl_id().equals(inventory.getIl_id())) break;
        }
        if(i < inventory1_len){
            inventory.setIl_id(inventory1.get(i).getIl_id());
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
        List<InventoryTransaction> transactions = inventoryTransactionMapper.getInventoryTransactionByUid(u_id);
        int transactions_len = transactions.size();
        for(int i = 0;i < transactions_len;i ++){
            InventoryTransaction inventoryTransaction = transactions.get(i);
            modifyforTransactionDetail(inventoryTransaction);
        }
        return transactions;
    }

    private void modifyforTransactionDetail(InventoryTransaction inventoryTransaction) {

        GoodsInfo goodsInfo = goodsInfoMapper.queryGoodsInfoByGid(inventoryTransaction.getG_id());
        String goods_name = goodsInfo.getName();
        Inventory s_inventory = getInventoryByNameAndIlId(goods_name, inventoryTransaction.getIl_id_s());

        if(s_inventory != null) {
            inventoryTransaction.setS_quantity(s_inventory.getQuantity());
        }else{
            inventoryTransaction.setS_quantity(0);
        }

        String s_inventory_name = inventoryMapper.queryWarehouseByIlID(inventoryTransaction.getIl_id_s()).getInventory_name();
        inventoryTransaction.setS_inventory_name(s_inventory_name);

        Inventory warehouse = inventoryMapper.queryWarehouseByIlID(inventoryTransaction.getIl_id_d());
        String d_inventory_name = warehouse.getInventory_name();

        // 原仓库名，目的仓库名，货物名
        inventoryTransaction.setD_inventory_name(d_inventory_name);
        inventoryTransaction.setGoods_name(goods_name);

        // 若目的仓库中有库存，则使用库存值，否则使用0
        Inventory d_inventory = getInventoryByNameAndIlId(goods_name, inventoryTransaction.getIl_id_d());
        if(d_inventory != null){
            inventoryTransaction.setD_quantity(d_inventory.getQuantity());
            // 若目的库中存在对应的库存
            inventoryTransaction.setI_id_d(d_inventory.getI_id());
        }else{
            inventoryTransaction.setD_quantity(0);
        }
    }

    private Inventory getInventoryByNameAndIlId(String name, Integer il_id){
        List<Inventory> inventory1 = this.selectInventoryByName(name);
        int k = 0;
        Integer inventory1_len = inventory1.size();
        for(;k<inventory1_len;k++){
            if(inventory1.get(k).getIl_id().equals(il_id)) break;
        }
        if(k<inventory1_len){
            return inventory1.get(k);
        }else{
            return null;
        }
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

    @Override
    public boolean deleteInventoryTransactionByItiId(Integer iti_id) {
        InventoryTransaction inventoryTransaction =  inventoryTransactionMapper.getInventoryTransactionByItiId(iti_id);
        modifyforTransactionDetail(inventoryTransaction);

        Inventory d_inventory = new Inventory();
        if(inventoryTransaction.getI_id_d() != null) {
            d_inventory = this.queryInventoryByIId(inventoryTransaction.getI_id_d());
        }else return false;

        if(d_inventory.getQuantity() < inventoryTransaction.getQuantity()){
            return false;
        }

        Inventory inventory = new Inventory();
        inventory.setQuality(d_inventory.getQuality());
        inventory.setIl_id(inventoryTransaction.getIl_id_s());
        inventory.setQuantity(inventoryTransaction.getQuantity());
        inventory.setG_id(d_inventory.getG_id());
        inventory.setName(d_inventory.getName());
        this.mergeInsertInventory(inventory);

        this.decreaseInventory(inventoryTransaction.getQuantity(), d_inventory);
        inventoryTransactionMapper.deleteInventoryTransactionByItiId(iti_id);
        return true;
    }

    @Override
    public void deleteInventoryTransactionByUId(Integer u_id) {
        inventoryTransactionMapper.deleteInventoryTransactionByUId(u_id);
    }

    @Override
    public void decreaseInventory(Integer decreaseQuantity, Inventory inventory) {
        if(decreaseQuantity >= inventory.getQuantity()){
            this.deleteInventoryByIID(inventory.getI_id());
        }else{
            inventory.setQuantity(inventory.getQuantity() - decreaseQuantity);
            this.updateInventory(inventory);
        }
    }

    @Override
    public void insertWarehouse(Inventory inventory) {
        inventoryMapper.insertWarehouse(inventory);
    }

    class WarehouseComparator implements Comparator<Inventory> {

        @Override
        public int compare(Inventory o1, Inventory o2) {
            return o1.getInventory_prior() - o2.getInventory_prior();
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    /**
     * 每次刷新inventoryView时，向model中添加库存和排好序的仓库
     * @param model
     */
    @Override
    public void updateInventoryView(Model model) {
        List<Inventory> inventories = this.queryInventoryWithGnameList();
        model.addAttribute("inventories", inventories);
        List<Inventory> inventory_lists = this.queryWarehouseList();
        inventory_lists.sort(new WarehouseComparator());
        model.addAttribute("inventory_lists", inventory_lists);
    }

    @Override
    public void updateWarehouse(Inventory inventory) {
        inventoryMapper.updateWarehouse(inventory);
    }
}
