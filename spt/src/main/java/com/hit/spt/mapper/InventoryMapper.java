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

    /**
     * 根据仓库ID查询仓库内货品信息
     *
     * @param il_id 仓库ID
     * @return {库存ID、货品ID、货品名、货品数量、单件成本}列表
     */
    List<Inventory> queryInventoryWithIlid(Integer il_id);

    Integer insertWarehouse(Inventory inventory);

    Integer insertInventoryWithGoodName(Inventory inventory);

    List<Inventory> selectInventoryByName(String name);

    Integer updateInventory(Inventory inventory);

    Inventory queryInventoryById(Integer i_id);

    Integer mergeInventory(Inventory inventory);

    Integer deleteInventoryByIId(Integer i_id);

    List<Inventory> queryWarehouseList();

    Inventory queryInventoryByIdAndIlID(Integer g_id, Integer il_id);

    Inventory queryWarehouseByIlID(Integer il_id);

    Integer updateWarehouse(Inventory inventory);
}
