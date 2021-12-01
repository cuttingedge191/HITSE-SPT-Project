package com.hit.spt.service.impl;

import com.hit.spt.mapper.UserMapper;
import com.hit.spt.pojo.User;
import com.hit.spt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper UserMapper;

    @Override
    public List<User> queryUserList() {
        return UserMapper.queryUserList();
    }

    @Override
    public User queryUserByUid(int u_id) {
        return UserMapper.queryUserByUid(u_id);
    }

    @Override
    public User queryUserByUsername(String username) {
        return UserMapper.queryUserByUsername(username);
    }

    @Override
    public void deleteUserByUid(int u_id) {
        UserMapper.deleteUserByUid(u_id);
    }

    @Override
    public int updateUser(User user) {
        return UserMapper.updateUser(user);
    }
}
