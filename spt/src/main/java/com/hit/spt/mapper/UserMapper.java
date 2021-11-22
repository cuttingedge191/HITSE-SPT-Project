package com.hit.spt.mapper;

import com.hit.spt.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    int insertUser(User user);

    List<User> queryUserList();

    List<User> queryUserByName(String name);

    // 为了验证登录信息
    User queryUserByUsername(String username);

}
