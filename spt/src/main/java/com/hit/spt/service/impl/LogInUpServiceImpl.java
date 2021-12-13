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

    /**
     * 根据用户信息进行注册
     *
     * @param user 用户信息
     * @return 改动的行数
     */
    @Override
    public boolean registerUser(User user) {
        if (checkUsernameIfExits(user.getUsername()))
            return false;
        user.setLevel(0);
        userMapper.insertUser(user);
        return true;
    }

    @Override
    public boolean checkUsernameIfExits(String username) {
        User user = userMapper.queryUserByUsername(username);
        return user != null;
    }
}
