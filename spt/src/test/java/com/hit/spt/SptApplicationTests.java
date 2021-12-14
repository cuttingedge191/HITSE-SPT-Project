package com.hit.spt;

import com.hit.spt.controller.CustomerController;
import com.hit.spt.mapper.CustomerMapper;
import com.hit.spt.mapper.OrderItemMapper;
import com.hit.spt.mapper.UserMapper;
import com.hit.spt.pojo.Customer;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.User;
import com.hit.spt.service.LogInUpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class SptApplicationTests {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    LogInUpService logInUpService;

//    @Test
////    public void testOrderItemMapper() {
////        Integer a = null;
////        System.out.println((int) a);
////    }
//    @Test
//    public void testCustomerMapper() {
//        int c_id = 22;
//        String name = "Chen", type = "wholesale", gender = "male", phone = "3338088", address = "Asia";
//        Customer customer = new Customer(c_id, type, name, gender, phone, address);
//        System.out.println(customerMapper.updateCustomerById(customer));
//    }
//
//    @Test
//    public void testUserMapper() {
//        //        System.out.println(userMapper.insertUser(new User(1,"Jian","male","Harbin",0,"Ali","123456")));
////        System.out.println(userMapper.insertUser(new User(2,"Lian","male","Harbin",0,"Bad","123456")));
//        System.out.println(userMapper.queryUserByUsername("aaa"));
//        System.out.println(userMapper.queryUserByUsername("Ali"));
//        System.out.println(userMapper.queryUserByName("Lian"));
//        System.out.println(userMapper.queryUserList());
//    }

    @Test
    public void testService() {
        System.out.println(logInUpService.checkUsernameIfExits("bbb"));
    }

}
