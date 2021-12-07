package com.hit.spt.mapper;

import com.hit.spt.pojo.InventoryTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InventoryTransactionMapper {
    List<InventoryTransaction> getInventoryTransactionByUid(Integer u_id);

    Integer insertInventoryTransaction(InventoryTransaction inventoryTransaction);

    InventoryTransaction getInventoryTransactionByItiId(Integer iti_id);

    Integer deleteInventoryTransactionByItiId(Integer iti_id);

    Integer deleteInventoryTransactionByUId(Integer u_id);
}
