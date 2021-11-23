package com.hit.spt.service;

import com.hit.spt.pojo.Inventory;

import java.util.List;

public interface InventoryService {
    int insertInventoryChange(Inventory inventory);

    List<Inventory> queryInventoryList();

    List<Inventory> queryInventoryWithGnameList();
}
