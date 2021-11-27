package com.hit.spt.service.impl;

import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @Override
    public List<GoodsInfo> getAllGoods(){
        return goodsInfoMapper.queryGoodsInfoList();
    }

    @Override
    public Integer insertGoods(GoodsInfo goodsInfo){

        return goodsInfoMapper.insertGoodsInfo(goodsInfo);
    }

    @Override
    public GoodsInfo queryGoodsInfoByName(String name){
        return goodsInfoMapper.queryGoodsInfoByName(name);
    }

    @Override
    public Integer deleteGoodsByGid(Integer g_id){
        return goodsInfoMapper.deleteGoodsInfoByGid(g_id);
    }
}
