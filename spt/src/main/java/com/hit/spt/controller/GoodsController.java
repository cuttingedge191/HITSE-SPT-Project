package com.hit.spt.controller;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping("addGoods")
    public String addGoods(Model model){
        return "addGoods";
    }

    @RequestMapping("addGoodsNow")
    public String ADCSGoods(GoodsInfo good, Model model){
        goodsService.insertGoods(good);
        List<GoodsInfo> goodsInfos = goodsService.getAllGoods();
        model.addAttribute("goods", goodsInfos);
        return "goodsView";
    }

    @RequestMapping("goodsView")
    public String goodsView(Model model) {
        List<GoodsInfo> goodsInfos = goodsService.getAllGoods();
        model.addAttribute("goods", goodsInfos);
        return "goodsView";
    }
}
