package com.hit.spt.service;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.Inventory;

import java.util.List;

public interface GoodsService {
    public List<GoodsInfo> getAllGoods();
    public Integer insertGoods(GoodsInfo goodsInfo);
    public GoodsInfo queryGoodsInfoByName(String name);
    public Integer deleteGoodsByGid(Integer g_id);
}
