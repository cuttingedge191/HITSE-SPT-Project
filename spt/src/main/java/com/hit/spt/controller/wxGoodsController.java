package com.hit.spt.controller;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 用于微信小程序的商品信息控制器

@RestController
public class wxGoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/mall/getGoodsList")
    public List<GoodsInfo> getGoodsList() {
        return goodsService.getAllGoods();
    }

    @RequestMapping("/mall/getGoodsInfoByGid")
    public GoodsInfo getGoodsInfoByGid(String g_id) {
        Long lg_id = Long.parseLong(g_id);
        return goodsService.queryGoodsInfoByGid(lg_id);
    }
}
