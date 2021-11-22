package com.hit.spt.service.impl;

import com.hit.spt.mapper.UserMapper;
import com.hit.spt.pojo.User;
import com.hit.spt.service.LogInUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogInUpServiceImpl implements LogInUpService {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean checkPassword(String username, String password) {
        User user = userMapper.queryUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public boolean registerUser(User user) {
        return userMapper.insertUser(user) > 0;
    }
}
