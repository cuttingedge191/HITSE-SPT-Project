package com.hit.spt.service;

import com.hit.spt.pojo.GoodsInfo;

import java.util.List;

public interface GoodsService {
    public List<GoodsInfo> getAllGoods();
    public Integer insertGoods(GoodsInfo goodsInfo);
}
