package com.hit.spt.service.impl;

import com.hit.spt.mapper.InventoryMapper;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public int insertInventoryChange(Inventory inventory) {
        assert inventoryMapper != null;
        return inventoryMapper.insertInventory(inventory);
    }

    @Override
    public List<Inventory> queryInventoryList(){
        return inventoryMapper.queryInventoryList();
    }

    @Override
    public List<Inventory> queryInventoryWithGnameList(){
        return inventoryMapper.queryInventoryWithGnameList();
    }
}
