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
    public String addGoods(Model model) {
        return "addGoods";
    }

    @RequestMapping("updateGoods")
    public String updateGoods(String g_id, Model model) {
        long lg_id = Long.parseLong(g_id);
        GoodsInfo good = goodsService.queryGoodsInfoByGid(lg_id);
        model.addAttribute("good", good);
        return "updateGoods";
    }

    @RequestMapping("updateGoodsNow")
    public String updateGoodsNow(String name, String g_id, String cost, String retail_price, String trade_price, String description, Model model) {
        // 参数不合法（应该不能通过前端检查），直接返回“货品管理”
        if (retail_price == null || trade_price == null) {
            return "redirect:goodsView";
        }
        Long lg_id = Long.parseLong(g_id);
        Double dRPrice = Double.parseDouble(retail_price);
        Double dTPrice = Double.parseDouble(trade_price);
        GoodsInfo good = new GoodsInfo(lg_id, name, (double) 0, dRPrice, dTPrice, description);
        goodsService.updateGoods(good);
        return "redirect:goodsView";
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
