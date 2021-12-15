package com.hit.spt.controller;

import com.hit.spt.service.OverViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping
public class OverViewController {
    @Autowired
    OverViewService overViewService;

    @RequestMapping("getInventoryOverView")
    @ResponseBody
    public List<String> getInventoryOverView(String il_id) {
        Integer il_id_int = Integer.parseInt(il_id);
        return overViewService.getInventoryOverView(il_id_int);
    }

    @RequestMapping("getSalesOverView")
    @ResponseBody
    public List<String> getSalesOverView(String g_id, String type) {
        if (type.equals("week"))
            return overViewService.getSalesOverView(g_id, 7);
        else
            return overViewService.getSalesOverView(g_id, 30);
    }
}
