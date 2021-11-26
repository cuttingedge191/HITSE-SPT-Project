package com.hit.spt.service;

import com.hit.spt.pojo.Inventory;

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
}
