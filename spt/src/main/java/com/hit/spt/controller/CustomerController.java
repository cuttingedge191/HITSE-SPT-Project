package com.hit.spt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hit.spt.mapper.CustomerMapper;
import com.hit.spt.pojo.Customer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    CustomerMapper customerMapper;

    @RequestMapping("customerInfo")
    public JSONPObject customerInfo() {
        JSONObject customerList = new JSONObject();
        return null;
    }
}
