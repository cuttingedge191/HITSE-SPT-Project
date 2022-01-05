package com.hit.spt.controller;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
public class GoodsController {
    @Autowired
    GoodsService goodsService;



    @RequestMapping("addGoods")
    public String addGoods() {
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
    public String updateGoodsNow(String name, String g_id, String retail_price, String trade_price, String description) {
        // 参数不合法（应该不能通过前端检查），直接返回“货品管理”
        if (retail_price == null || trade_price == null) {
            return "redirect:goodsView";
        }
        Long lg_id = Long.parseLong(g_id);
        Double dRPrice = Double.parseDouble(retail_price);
        Double dTPrice = Double.parseDouble(trade_price);
        GoodsInfo good = new GoodsInfo(lg_id, name, (double) -1, dRPrice, dTPrice, description);
        goodsService.updateGoods(good);
        return "redirect:goodsView";
    }

    @RequestMapping("addGoodsNow")
    public String addGoodsNow(@RequestParam("image") MultipartFile image, GoodsInfo good,Model model, HttpServletRequest request){
        GoodsInfo goodsInfo1 = goodsService.queryGoodsInfoByName(good.getName());
        GoodsInfo goodsInfo2 = goodsService.queryGoodsInfoByGid(good.getG_id());
        String uri = request.getRequestURI();
        if (goodsInfo1 == null && goodsInfo2 == null) {
            goodsService.insertGoods(good);
            String[] originalFileName = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");
            String  destDir=request.getServletContext().getRealPath("static/");
            destDir = destDir + "goodsImages/";
            File destpath = new File(destDir, good.getG_id() +
                    "." + originalFileName[originalFileName.length - 1]);
            // 判断路径是否存在，如果不存在就创建一个
            if (!destpath.getParentFile().exists()) {
                destpath.getParentFile().mkdirs();
            }
            String path = destDir + good.getG_id() +
                    "." + originalFileName[originalFileName.length - 1];
            System.out.println(path);
            try{
                image.transferTo(new File(path));
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            model.addAttribute("goodsExistWarning", "true");
            return "addGoods";
        }
        return "redirect:goodsView";
    }

    @RequestMapping("deleteGoods")
    public String ADCSGoods(GoodsInfo good, Model model, HttpServletRequest request){
        String uri = request.getRequestURI();
        goodsService.deleteGoodsByGid(good.getG_id());
        return "redirect:goodsView";
    }

    @RequestMapping("goodsView")
    public String goodsView(Model model) {
        List<GoodsInfo> goodsInfos = goodsService.getAllGoods();
        model.addAttribute("goods", goodsInfos);
        return "goodsView";
    }
}
