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

    Integer updateInventory(Inventory inventory);

    Inventory queryInventoryByIId(Integer i_id);

    Integer queryQuantityByGid(Long g_id);

    Integer insertInventoryWithGoodName(Inventory inventory);

    List<Inventory> selectInventoryByName(String name);

    Integer mergeInventory(Inventory inventory);

    Integer deleteInventoryByIID(Integer i_id);

    List<Inventory> queryWarehouseList();

    Inventory queryInventoryByIdAndIlID(Integer g_id, Integer il_id);

    Integer mergeInsertInventory(Inventory inventory);

    List<InventoryTransaction> getInventoryTransactionWithUid(Integer u_id);

    Integer insertInventoryTransaction(InventoryTransaction inventoryTransaction);

    public void refreshInventoryTransView(Model model, HttpServletRequest httpServletRequest);

    public boolean deleteInventoryTransactionByItiId(Integer iti_id);

    public void deleteInventoryTransactionByUId(Integer u_id);

    public void decreaseInventory(Integer decreaseQuantity, Inventory inventory);

    public void insertWarehouse(Inventory inventory);

    public void updateInventoryView(Model model);

    public void updateWarehouse(Inventory inventory);
}
