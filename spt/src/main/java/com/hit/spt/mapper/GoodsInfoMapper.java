package com.hit.spt.mapper;


import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsInfoMapper {
    Integer insertGoodsInfo(GoodsInfo goodsInfo);

    Integer updateGoodsInfo(GoodsInfo goodsInfo);

    Integer updateGoodsCost(GoodsInfo goodsInfo);

    GoodsInfo queryGoodsInfoByName(String name);

    GoodsInfo queryGoodsInfoByGid(Long g_id);

    Integer deleteGoodsInfoByGid(Long g_id);

    List<GoodsInfo> queryGoodsInfoList();

    Inventory queryInventoryById(Integer i_id);
}
