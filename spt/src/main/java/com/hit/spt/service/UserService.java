package com.hit.spt.service;

import com.hit.spt.pojo.User;

import java.util.List;

public interface UserService {
    List<User> queryUserList();

    User queryUserByUid(int u_id);

    void deleteUserByUid(int u_id);

    int updateUser(User user);
}
