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
        return inventoryMapper.updateInventory(inventory);
    }

    @Override
    public Inventory queryInventoryById(Integer i_id){
        return inventoryMapper.queryInventoryById(i_id);
    }

    @Override
    public Integer insertInventoryWithGoodName(Inventory inventory){
        return inventoryMapper.insertInventoryWithGoodName(inventory);
    }

    @Override
    public List<Inventory> selectInventoryByName(String name){
        return inventoryMapper.selectInventoryByName(name);
    }

    @Override
    public Integer mergeInventory(Inventory inventory){
        return inventoryMapper.mergeInventory(inventory);
    }
}
