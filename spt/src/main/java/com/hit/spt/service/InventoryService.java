package com.hit.spt.service;

import com.hit.spt.pojo.Inventory;
import com.hit.spt.pojo.InventoryTransaction;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface InventoryService {
    int insertInventoryChange(Inventory inventory);

    List<Inventory> queryInventoryList();

    List<Inventory> queryInventoryWithGnameList();

    public Integer updateInventory(Inventory inventory);

    public Inventory queryInventoryById(Integer i_id);

    public Integer insertInventoryWithGoodName(Inventory inventory);

    public List<Inventory> selectInventoryByName(String name);

    public Integer mergeInventory(Inventory inventory);

    public Integer deleteInventoryByIID(Integer i_id);

    public List<Inventory> queryWarehouseList();

    Inventory queryInventoryByIdAndIlID(Integer g_id, Integer il_id);

    public Integer mergeInsertInventory(Inventory inventory);

    public List<InventoryTransaction> getInventoryTransactionWithUid(Integer u_id);

    public Integer insertInventoryTransaction(InventoryTransaction inventoryTransaction);

    public void refreshInventoryTransView(Model model, HttpServletRequest httpServletRequest);

    public void deleteInventoryTransactionByItiId(Integer iti_id);

    public void deleteInventoryTransactionByUId(Integer u_id);
}
