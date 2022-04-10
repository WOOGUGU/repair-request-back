package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.UserDao;
import com.kkkoke.networkrepair.exception.PasswordWrongException;
import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Role;
import com.kkkoke.networkrepair.pojo.User;
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
    public User addUser(String username, String password, String name, Integer roleType, String tel) throws UserHasExistedException {
        // 查看数据库中是否已经存在此用户
        if (ObjectUtils.isEmpty(userDao.selectUserByUsername(username))) {
            // 对密码进行BCrypt加密
            String hashPasswd = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, hashPasswd, name, tel);
            userDao.addUser(user);
            userDao.setRole(userDao.selectUserByUsername(username).getId(), roleType);
            return user;
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
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(user)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return user;
        }
    }

    // 搜索用户 后台搜索接口
    @Override
    public List<User> selectUser(Integer userId, String username, String name, Integer roleId, String tel) throws UserHasNotExistedException {
        return userDao.selectUser(userId, username, name, roleId, tel);
    }

    // 查找所有用户
    @Override
    public List<User> selectAllUser() throws UserHasNotExistedException {
        List<User> users = userDao.selectAllUser();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(users)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            for (User user : users) {
                List<Role> roles = userDao.getRolesByUid(user.getId());
                user.setRoles(roles);
            }
            return users;
        }
    }

    // 查找所有管理员
    @Override
    public List<User> selectAllAdmin() throws UserHasNotExistedException {
        List<User> admins = userDao.selectAllAdmin();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(admins)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return admins;
        }
    }

    // 查找所有维修员
    @Override
    public List<User> selectAllRepairman() throws UserHasNotExistedException {
        List<User> repairmans = userDao.selectAllRepairman();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(repairmans)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return repairmans;
        }
    }

    // 查找所有普通用户
    @Override
    public List<User> selectAllNorUser() throws UserHasNotExistedException {
        List<User> users = userDao.selectAllNorUser();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(users)) {
            throw new UserHasNotExistedException("User has not existed");
        } else {
            return users;
        }
    }

    // 修改用户信息
    @Override
    public User updateUser(Integer userId, String username, String password, String name, Integer roleType, String tel) throws UserHasNotExistedException, PasswordWrongException {
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
                userDao.updateRole(userId, roleType);
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
