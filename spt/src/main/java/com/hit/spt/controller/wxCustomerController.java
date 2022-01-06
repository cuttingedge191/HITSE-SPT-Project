package com.hit.spt.controller;

import com.alibaba.fastjson.JSONObject;
import com.hit.spt.pojo.Customer;
import com.hit.spt.service.impl.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/mall/changeInfo")
    public String changeInfo(@RequestBody JSONObject jsonObject) {
        String password = jsonObject.getString("password");
        String password_confirm = jsonObject.getString("password_confirm");
        String phone = jsonObject.getString("phone");
        Customer c = clientInfoService.queryCustomerByPhone(phone);
        c.setName(jsonObject.getString("name"));
        c.setGender(jsonObject.getString("gender").equals("1") ? "male" : "female");
        c.setAddress(jsonObject.getString("address"));
        if (password.equals("") && password_confirm.equals("")) {
            clientInfoService.updateCustomer(c);
            return "pswNotChange";
        } else if (password.equals(password_confirm)) {
            clientInfoService.updateCustomer(c);
            c.setPassword(password);
            return "resetPsw";
        } else return "error";
    }

    @RequestMapping("/mall/login")
    public List<String> login(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");
        List<String> res = new ArrayList<>();
        Customer c = clientInfoService.queryCustomerByPhone(phone);
        if (c != null && c.getPassword().equals(password)) {
            res.add(c.getC_id().toString());
            res.add(c.getType());
            return res;
        }
        res.add("error");
        res.add("error");
        return res;
    }

    @RequestMapping("/mall/getCustomerInfoByCid")
    public Customer getCustomerInfoByCid(String c_id) {
        int ic_id = Integer.parseInt(c_id);
        return clientInfoService.queryCustomerById(ic_id);
    }

    @RequestMapping("/mall/getCustomerAddressByCid")
    public List<String> getCustomerAddressByCid(String c_id) {
        // List<String>并不代表有多个收货地址，只是String中文编码在微信小程序端有问题
        int ic_id = Integer.parseInt(c_id);
        Customer c = clientInfoService.queryCustomerById(ic_id);
        List<String> res = new ArrayList<>();
        res.add(c.getAddress());
        return res;
    }
}
