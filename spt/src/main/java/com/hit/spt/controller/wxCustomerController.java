package com.hit.spt.controller;

import com.alibaba.fastjson.JSONObject;
import com.hit.spt.pojo.Customer;
import com.hit.spt.service.impl.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class wxCustomerController {
    @Autowired
    ClientInfoService clientInfoService;

    @RequestMapping("/mall/logup")
    public String logup(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        Customer c = clientInfoService.queryCustomerByPhone(phone);
        // 如果无此用户或者用户尚未在微信小程序注册，则进行注册
        if (c == null || c.getPassword() == null) {
            String name = jsonObject.getString("name");
            String gender = jsonObject.getString("gender").equals("1") ? "male" : "female";
            String address = jsonObject.getString("address");
            String password = jsonObject.getString("password");
            if (c == null) {
                // 注册新客户，默认为零售客户
                Customer newCustomer = new Customer(null, "retail", name, gender, phone, address, password);
                clientInfoService.insertCustomer(newCustomer);
            } else {
                c.setName(name);
                c.setGender(gender);
                c.setAddress(address);
                c.setPassword(password);
                clientInfoService.updateCustomer(c);
            }
            return "ok";
        }
        return "error";
    }

    @RequestMapping("/mall/login")
    public String login(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");
        Customer c = clientInfoService.queryCustomerByPhone(phone);
        if (c != null && c.getPassword().equals(password)) {
            return c.getC_id().toString();
        }
        return "error";
    }

    @RequestMapping("/mall/getCustomerInfoByCid")
    public Customer getCustomerInfoByCid(String c_id) {
        int ic_id = Integer.parseInt(c_id);
        return clientInfoService.queryCustomerById(ic_id);
    }
}
