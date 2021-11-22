package com.hit.spt;

import com.hit.spt.controller.CustomerController;
import com.hit.spt.mapper.CustomerMapper;
import com.hit.spt.mapper.UserMapper;
import com.hit.spt.pojo.Customer;
import com.hit.spt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class SptApplicationTests {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    UserMapper userMapper;

    @Test
    public void testCustomerMapper() {
//        System.out.println(customerMapper.deleteCustomerById(1));
//        System.out.println(userMapper.insertUser(new User(1,"Jian","male","Harbin",0,"Ali","123456")));
//        System.out.println(userMapper.insertUser(new User(2,"Lian","male","Harbin",0,"Bad","123456")));
        System.out.println(userMapper.queryUserByUsername("aaa"));
        System.out.println(userMapper.queryUserByUsername("Ali"));
        System.out.println(userMapper.queryUserByName("Lian"));
        System.out.println(userMapper.queryUserList());
    }

}
