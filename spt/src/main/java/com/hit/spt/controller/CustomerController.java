package com.hit.spt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hit.spt.mapper.CustomerMapper;
import com.hit.spt.pojo.Customer;
import com.hit.spt.service.impl.ClientInfoService;
import org.apache.coyote.Request;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping
public class CustomerController {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ClientInfoService clientInfoService;

    @RequestMapping({"addCustomerNow", "upcosNow", "clientInfoSearch", "decos"})
    public String addCustomerNow(String c_id, String name, String phone,String addr, String sex, String type, Model model, HttpServletRequest request){
        String uri = request.getRequestURI();
//        System.out.println(uri);
        // 删除客户
        if (uri.charAt(1) == 'd'){
            int ic_id = Integer.parseInt(c_id);
            clientInfoService.deleteCustomer(ic_id);
        }
        // 参数不合法（应该不能通过前端检查），直接返回“客户管理”
        if(name == null || phone == null || addr == null || sex == null || type == null){
            List<Customer> customers = clientInfoService.showAllCustoms();
            model.addAttribute("customers", customers);
            return "clientInfoSearch";
        }
        // 客户电话错误直接返回
        String CHINA_REGEX_EXP = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";
        Pattern p = Pattern.compile(CHINA_REGEX_EXP);
        Matcher m = p.matcher(phone);
        boolean isChinesePhoneNum = m.matches();
        if (isChinesePhoneNum == false) {
            model.addAttribute("msg", "电话号码不符合格式!");
            if (uri.charAt(1) == 'u') {
                int ic_id = Integer.parseInt(c_id);
                Customer customer = clientInfoService.queryCustomerById(ic_id);
                model.addAttribute("customer", customer);
                return "updateCustomer";
            }
            if (uri.charAt(1) == 'a') {
                return "addCustomer";
            }
        }
        // 将表单返回内容抓换成数据库的格式
        String gender = sex.equals("true") ? "male" : "female";
        String c_type = type.equals("true") ? "retail" : "trade";
        Customer customer = null;
        // 添加客户
        if (uri.charAt(1) == 'a'){
            customer = new Customer(null, c_type ,name ,gender ,phone ,addr);
            clientInfoService.insertCustomer(customer);
        }
        // 更新客户
        else if(uri.charAt(1) == 'u'){
            customer = new Customer(Integer.parseInt(c_id), c_type ,name ,gender ,phone ,addr);
            clientInfoService.updateCustomer(customer);
        }
        List<Customer> customers = clientInfoService.showAllCustoms();
        model.addAttribute("customers", customers);
        return "redirect:clientInfoSearch";
    }

    @RequestMapping("addCustomer")
    public String addCustomer() {
        return "addCustomer";
    }

    @RequestMapping("upcos")
    public String upcos(String c_id, Model model){
        int ic_id = Integer.parseInt(c_id);
        Customer customer = clientInfoService.queryCustomerById(ic_id);
        model.addAttribute("customer", customer);
        return "updateCustomer";
    }
}
