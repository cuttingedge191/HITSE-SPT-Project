package com.hit.spt.service.impl;

import com.hit.spt.mapper.InventoryMapper;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.service.AddInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddInventoryServiceImpl implements AddInventoryService {
    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public int insertInventoryChange(Inventory inventory) {
        assert inventoryMapper != null;
        return inventoryMapper.insertInventory(inventory);
    }
}