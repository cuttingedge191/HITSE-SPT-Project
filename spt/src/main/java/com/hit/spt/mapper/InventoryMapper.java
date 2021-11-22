package com.hit.spt.mapper;

import com.hit.spt.pojo.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InventoryMapper {
    int insertInventory(Inventory inventory);

    int deleteInventoryByGid(Integer g_id);

    Inventory queryInventoryByGid(Integer g_id);

    List<Inventory> queryInventoryList();
}
