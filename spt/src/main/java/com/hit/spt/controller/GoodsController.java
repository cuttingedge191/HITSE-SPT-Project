package com.hit.spt.controller;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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


    @RequestMapping({"addGoodsNow", "deleteGoods"})
    public String ADCSGoods(GoodsInfo good, Model model, HttpServletRequest request){
        String uri = request.getRequestURI();
        if (uri.charAt(1) == 'a') {
            GoodsInfo goodsInfo1 = goodsService.queryGoodsInfoByName(good.getName());
            if (goodsInfo1 == null) {
                goodsService.insertGoods(good);
            } else {
                model.addAttribute("goodsExistWarning", "true");
                return "addGoods";
            }
        }else if (uri.charAt(1) == 'd'){
            goodsService.deleteGoodsByGid(good.getG_id());
        }
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
