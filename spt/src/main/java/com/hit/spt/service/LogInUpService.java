package com.hit.spt.service;

import com.hit.spt.pojo.User;

public interface LogInUpService {

    boolean checkPassword(String username, String password);

    boolean registerUser(User user);

}
