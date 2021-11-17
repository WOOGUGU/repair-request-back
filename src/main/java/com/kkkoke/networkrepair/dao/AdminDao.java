package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao {
    // 添加管理员
    int addAdmin(Admin admin);

    // 删除管理员
    int deleteAdmin(Long id);

    // 通过用户名查找管理员
    Admin selectAdminByUsername(String username);

    // 通过id查找管理员
    Admin selectAdminById(Long id);

    // 查找所有管理员
    List<Admin> selectAllAdmin();

    // 修改管理员信息
    Integer updateAdmin(Admin admin);
}
