package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.UserDao;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    // 添加用户
    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    // 删除用户
    @Override
    public int deleteUser(Long id) {
        return userDao.deleteUser(id);
    }

    @Override
    // 通过用户名查找用户
    public User selectUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    @Override
    // 通过id查找用户
    public User selectUserById(Long id) {
        return userDao.selectUserById(id);
    }

    @Override
    // 查找所有用户
    public List<User> selectAllUser() {
        return userDao.selectAllUser();
    }

    @Override
    // 修改用户信息
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }
}
