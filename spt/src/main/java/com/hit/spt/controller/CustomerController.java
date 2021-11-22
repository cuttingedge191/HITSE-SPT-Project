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

@Controller
@RequestMapping
public class CustomerController {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ClientInfoService clientInfoService;

    @RequestMapping({"addCustomerNow", "upcosNow"})
    public String addCustomerNow(String c_id, String name, String phone,String addr, String sex, String type, Model model, HttpServletRequest request){
        String uri = request.getRequestURI();
//        System.out.println(uri);
        if(name == null || phone == null || addr == null || sex == null || type == null){
            List<Customer> customers = clientInfoService.showAllCustoms();
            model.addAttribute("customers", customers);
            return "clientInfoSearch";
        }
        String gender = sex.equals("true") ? "male" : "female";
        String c_type = type.equals("true") ? "retail" : "bulk";
        Customer customer = null;
        if (uri.charAt(1) == 'a'){
            customer = new Customer(null, c_type ,name ,gender ,phone ,addr);
            clientInfoService.insertCustomer(customer);
        }
        else if(uri.charAt(1) == 'u'){
            customer = new Customer(Integer.parseInt(c_id), c_type ,name ,gender ,phone ,addr);
            clientInfoService.updateCustomer(customer);
        }
        List<Customer> customers = clientInfoService.showAllCustoms();
        model.addAttribute("customers", customers);
        return "clientInfoSearch";
    }

    @RequestMapping("clientInfoSearch")
    public String clientInfoSearch(Model model) {
        List<Customer> customers = clientInfoService.showAllCustoms();
        model.addAttribute("customers", customers);
        return "clientInfoSearch";
    }

    @RequestMapping("addCustomer")
    public String addCustomer() {
        return "addCustomer";
    }

    @RequestMapping("decos")
    public String decos(String c_id, Model model){
        int ic_id = Integer.parseInt(c_id);
        clientInfoService.deleteCustomer(ic_id);
        List<Customer> customers = clientInfoService.showAllCustoms();
        model.addAttribute("customers", customers);
        return "clientInfoSearch";
    }

    @RequestMapping("upcos")
    public String upcos(String c_id, Model model){
        int ic_id = Integer.parseInt(c_id);
        Customer customer = clientInfoService.queryCustomerById(ic_id);
        model.addAttribute("customer", customer);
        return "updateCustomer";
    }
//    @RequestMapping("upcosNow")
//    public String upcosNow()
}
