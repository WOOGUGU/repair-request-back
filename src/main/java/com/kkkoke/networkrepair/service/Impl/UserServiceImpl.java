package com.kkkoke.networkrepair.service.Impl;

import com.github.pagehelper.PageHelper;
import com.kkkoke.networkrepair.dao.UserDao;
import com.kkkoke.networkrepair.exception.PasswordWrongException;
import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Role;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.result.ResultPage;
import com.kkkoke.networkrepair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    // 添加用户
    @Override
    public Integer addUser(String username, String password, String name, List<Integer> roleTypes, String tel) throws UserHasExistedException {
        // 查看数据库中是否已经存在此用户
        if (ObjectUtils.isEmpty(userDao.selectUserByUsername(username))) {
            // 对密码进行BCrypt加密
            String hashPasswd = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, hashPasswd, name, tel);
            userDao.addUser(user);
            Integer userId = userDao.selectUserByUsername(username).getId();
            for (Integer roleId : roleTypes) {
                userDao.setRole(userId, roleId);
            }
            return userId;
        } else {
            // 用户已存在
            throw new UserHasExistedException("User has existed");
        }
    }

    // 删除用户
    @Override
    public int deleteUser(Integer userId, String username) throws UserHasNotExistedException {
        // 查询数据库，查看要删除的用户是否存在
        if (ObjectUtils.isEmpty(userDao.selectUserById(userId)) && ObjectUtils.isEmpty(userDao.selectUserByUsername(username))) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            userDao.deleteRole(userId);
            return userDao.deleteUser(userId, username);
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
        user.setPassword(null);
        user.setRoles(userDao.getRolesByUid(userId));
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(user)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return user;
        }
    }

    // 搜索用户 后台搜索接口
    @Override
    public ResultPage<User> selectUser(Integer userId, String username, String name, Integer roleId, String tel, Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectUser(userId, username, name, roleId, tel);
        ResultPage<User> resultPage = ResultPage.restPage(users);
        return resultPage;
    }

    // 查找所有用户
    @Override
    public ResultPage<User> selectAllUser(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectAllUser();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(users)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            for (User user : users) {
                List<Role> roles = userDao.getRolesByUid(user.getId());
                user.setRoles(roles);
            }
            return ResultPage.restPage(users);
        }
    }

    // 查找所有管理员
    @Override
    public ResultPage<User> selectAllAdmin(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> admins = userDao.selectAllAdmin();
        ResultPage<User> resultPage = ResultPage.restPage(admins);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(admins)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return resultPage;
        }
    }

    // 查找所有维修员
    @Override
    public ResultPage<User> selectAllRepairman(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> repairmans = userDao.selectAllRepairman();
        ResultPage<User> resultPage = ResultPage.restPage(repairmans);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(repairmans)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return resultPage;
        }
    }

    // 查找所有普通用户
    @Override
    public ResultPage<User> selectAllNorUser(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectAllNorUser();
        ResultPage<User> resultPage = ResultPage.restPage(users);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(users)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return resultPage;
        }
    }

    // 修改用户信息
    @Override
    public User updateUser(Integer userId, String username, String password, String name, List<Integer> roleType, String tel) throws UserHasNotExistedException, PasswordWrongException {
        User user = userDao.selectUserById(userId);
        // 查找数据库中是否存在此用户
        if (ObjectUtils.isEmpty(user)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            // 如果用户存在就更新数据
            if (!ObjectUtils.isEmpty(username)) {
                user.setUsername(username);
            }
            if (!ObjectUtils.isEmpty(password)) {
                // 对密码进行BCrypt加密
                String passwd = BCrypt.hashpw(password, BCrypt.gensalt());
                user.setPassword(passwd);
            }
            if (!ObjectUtils.isEmpty(name)) {
                user.setName(name);
            }
            if (!ObjectUtils.isEmpty(tel)) {
                user.setTel(tel);
            }
            userDao.updateUser(user);
            if (!ObjectUtils.isEmpty(roleType)) {
                userDao.deleteRole(userId);
                for (Integer roleId : roleType) {
                    userDao.setRole(userId, roleId);
                }
            }
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
