package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Role;
import com.kkkoke.networkrepair.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    // 添加用户
    int addUser(User user);

    // 设置权限
    int setRole(Integer userId, Integer roleType);

    // 更新权限
    int updateRole(Integer userId, List<Integer> roleType);

    // 删除权限
    int deleteRole(Integer userId);

    // 删除用户
    int deleteUser(Integer id, String username);

    // 通过用户名查找用户
    User selectUserByUsername(String username);

    // 通过id查找用户
    User selectUserById(Integer id);

    // 搜索用户 后台搜索接口
    List<User> selectUser(Integer id, String username, String name, Integer roleId, String tel);

    // 查找所有用户
    List<User> selectAllUser();

    // 查找所有管理员
    List<User> selectAllAdmin();

    // 查找所有维修员
    List<User> selectAllRepairman();

    // 查找所有普通用户
    List<User> selectAllNorUser();

    // 修改用户信息
    Integer updateUser(User user);

    // 内部调用 根据⽤户名查询⽤户
    User loadUserByUsername(String username);

    // 根据⽤户id查询⻆⾊
    List<Role> getRolesByUid(Integer uid);
}
