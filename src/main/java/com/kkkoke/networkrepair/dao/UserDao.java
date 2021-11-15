package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    // 添加用户
    int addUser(User user);

    // 通过用户名删除用户
    int deleteUserByUsername(String username);

    // 通过id删除用户
    int deleteUserById(Long id);

    // 通过用户名查找用户
    User selectUserByUsername(String username);

    // 通过id查找用户
    User selectUserById(Long id);

    // 查找所有用户
    List<User> selectAllUser();

    // 修改用户信息
    User updateUser(User user);
}
