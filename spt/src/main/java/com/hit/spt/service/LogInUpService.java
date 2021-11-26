package com.hit.spt.service;

import com.hit.spt.pojo.User;

public interface LogInUpService {
    /**
     * 检查密码用户名是否匹配
     *
     * @param username 用户名
     * @param password 密码
     * @return 匹配则 true, 反之 false
     */
    boolean checkPassword(String username, String password);

    /**
     * 添加用户信息，即注册
     *
     * @param user 用户信息
     * @return 是否注册成功
     */
    boolean registerUser(User user);

    boolean checkUsernameIfExits(String user);

}
