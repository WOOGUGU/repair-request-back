package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.PasswordWrongException;
import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Role;
import com.kkkoke.networkrepair.pojo.User;

import java.util.List;

public interface UserService {
    // 添加用户
    User addUser(String username, String password, String name, Integer roleType) throws UserHasExistedException;

    // 删除用户
    int deleteUser(Integer userId, String username) throws UserHasNotExistedException;

    // 通过用户名查找用户
    User selectUserByUsername(String username) throws UserHasNotExistedException;

    // 通过id查找用户
    User selectUserById(Integer userId) throws UserHasNotExistedException;

    // 搜索用户 后台搜索接口
    List<User> selectUser(Integer id, String username, String name, Integer roleId) throws UserHasNotExistedException;

    // 查找所有用户
    List<User> selectAllUser() throws UserHasNotExistedException;

    // 查找所有管理员
    List<User> selectAllAdmin() throws UserHasNotExistedException;

    // 查找所有维修员
    List<User> selectAllRepairman() throws UserHasNotExistedException;;

    // 查找所有普通用户
    List<User> selectAllNorUser() throws UserHasNotExistedException;;

    // 修改用户信息
    User updateUser(Integer userId, String username, String password, String name, Integer roleType) throws UserHasNotExistedException, PasswordWrongException;

    // 内部调用 根据⽤户名查询⽤户
    User loadUserByUsername(String username);

    // 根据⽤户id查询⻆⾊
    List<Role> getRolesByUid(Integer userId);
}
