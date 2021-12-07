package com.hit.spt.mapper;

import com.hit.spt.pojo.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InventoryMapper {
    int insertInventory(Inventory inventory);

    int deleteInventoryByGid(Long g_id);

    List<Integer> queryQuantityByGid(Long g_id);

    List<Inventory> queryInventoryList();

    List<Inventory> queryInventoryWithGnameList();

    Integer insertInventoryWithGoodName(Inventory inventory);

    List<Inventory> selectInventoryByName(String name);

    Integer updateInventory(Inventory inventory);

    Inventory queryInventoryByIId(Integer i_id);

    Integer mergeInventory(Inventory inventory);

    Integer deleteInventoryByIId(Integer i_id);

    List<Inventory> queryWarehouseList();

    Inventory queryInventoryByIdAndIlID(Integer g_id, Integer il_id);

    Inventory queryWarehouseByIlID(Integer il_id);
}
