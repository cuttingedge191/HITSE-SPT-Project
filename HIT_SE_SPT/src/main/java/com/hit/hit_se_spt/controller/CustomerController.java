package com.hit.hit_se_spt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("customer")
public class CustomerController {
    @RequestMapping("customerInfo")
    public JSONPObject customerInfo() {


        JSONObject customerList = new JSONObject();
        return null;
    }
}
