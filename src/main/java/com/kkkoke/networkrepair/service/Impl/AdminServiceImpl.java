package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.AdminDao;
import com.kkkoke.networkrepair.pojo.Admin;
import com.kkkoke.networkrepair.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    // 添加管理员
    public int addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    @Override
    // 删除管理员
    public int deleteAdmin(Long id) {
        return adminDao.deleteAdmin(id);
    }

    @Override
    // 通过用户名查找管理员
    public Admin selectAdminByUsername(String username) {
        return adminDao.selectAdminByUsername(username);
    }

    @Override
    // 通过id查找管理员
    public Admin selectAdminById(Long id) {
        return adminDao.selectAdminById(id);
    }

    @Override
    // 查找所有管理员
    public List<Admin> selectAllAdmin() {
        return adminDao.selectAllAdmin();
    }

    @Override
    // 修改管理员信息
    public Integer updateAdmin(Admin admin) {
        return adminDao.updateAdmin(admin);
    }
}
