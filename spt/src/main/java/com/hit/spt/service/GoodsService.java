package com.hit.spt.service;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.Inventory;

import java.util.List;

public interface GoodsService {
    List<GoodsInfo> getAllGoods();
    Integer insertGoods(GoodsInfo goodsInfo);
    Integer updateGoods(GoodsInfo goodsInfo);
    Integer updateCost(GoodsInfo goodsInfo);
    GoodsInfo queryGoodsInfoByName(String name);
    GoodsInfo queryGoodsInfoByGid(Long g_id);
    Integer deleteGoodsByGid(Long g_id);
}
