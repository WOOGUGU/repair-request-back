package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.UserDao;
import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Role;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    // 添加用户
    @Override
    public User addUser(String username, String password, String name) throws UserHasExistedException {
        // 查看数据库中是否已经存在此用户
        if (ObjectUtils.isEmpty(userDao.selectUserByUsername(username))) {
            User user = new User(username, password, name);
            userDao.addUser(user);
            return user;
        } else {
            // 用户已存在
            throw new UserHasExistedException("User has existed");
        }
    }

    // 删除用户
    @Override
    public int deleteUser(Integer userId) throws UserHasNotExistedException {
        // 查询数据库，查看要删除的用户是否存在
        if (ObjectUtils.isEmpty(userDao.selectUserById(userId))) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return userDao.deleteUser(userId);
        }
    }

    // 通过用户名查找用户
    @Override
    public User selectUserByUsername(String username) throws UserHasNotExistedException {
        // 根据用户名查找用户
        User user = userDao.selectUserByUsername(username);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(user)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return user;
        }
    }

    // 通过id查找用户
    @Override
    public User selectUserById(Integer userId) throws UserHasNotExistedException {
        // 根据id查找用户
        User user = userDao.selectUserById(userId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(user)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return user;
        }
    }

    // 查找所有用户
    @Override
    public List<User> selectAllUser() throws UserHasNotExistedException {
        List<User> users = userDao.selectAllUser();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(users)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return users;
        }
    }

    // 修改用户信息
    @Override
    public User updateUser(Integer userId, String username, String password, String name) throws UserHasNotExistedException {
        // 创建要修改的user对象
        User user = new User(userId, username, password, name);
        // 查找数据库中是否存在此用户
        if (ObjectUtils.isEmpty(userDao.selectUserById(userId))) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            // 如果用户存在就更新数据
            userDao.updateUser(user);
            return user;
        }
    }

    // 内部调用 通过用户名查找用户
    @Override
    public User loadUserByUsername(String username) {
        return userDao.loadUserByUsername(username);
    }

    // 根据⽤户id查询⻆⾊
    @Override
    public List<Role> getRolesByUid(Integer userId) {
        return userDao.getRolesByUid(userId);
    }
}
