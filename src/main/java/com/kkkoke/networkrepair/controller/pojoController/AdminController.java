package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.pojo.Admin;
import com.kkkoke.networkrepair.service.AdminService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 添加管理员
    @PostMapping("/addAdmin")
    public StatusAndDataFeedback addAdmin(String username, String password, String name) {
        if (Objects.equals(username, "") || Objects.equals(password, "") || Objects.equals(name, "")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        Admin admin = new Admin(username, password, name);
        // 查看数据库中是否已经存在此管理员
        if (Objects.equals(adminService.selectAdminByUsername(username), null)) {
            // 调用service层添加管理员
            adminService.addAdmin(admin);
            // 返回给前端添加的管理员数据及处理的状态值
            return new StatusAndDataFeedback(admin, "handle_success");
        }
        else {
            // 数据库中已经存在此数据
            return new StatusAndDataFeedback(admin, "data_exist");
        }
    }

    // 通过用户名删除管理员
    @PostMapping("/deleteAdmin")
    public StatusAndDataFeedback deleteAdmin(Long id) {
        // 判断前端传过来的参数是否为空
        if (id == null) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 查询数据库，查看要删除的管理员是否存在
        if (Objects.equals(adminService.selectAdminById(id), null)) {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
        else {
            adminService.deleteAdmin(id);
        }

        return new StatusAndDataFeedback(null, "handle_success");
    }

    // 通过用户名查找管理员
    @PostMapping("/selectAdminByUsername")
    public StatusAndDataFeedback selectAdminByUsername(String username) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(username, null)) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 根据用户名查找管理员
        Admin admin = adminService.selectAdminByUsername(username);
        // 判断查询结果是否为空
        if (Objects.equals(adminService.selectAdminByUsername(username), null)) {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
        else {
            return new StatusAndDataFeedback(admin, "handle_success");
        }
    }

    // 通过id查找管理员
    @PostMapping("/selectAdminById")
    public StatusAndDataFeedback selectAdminById(Long id) {
        // 判断前端传过来的参数是否为空
        if (id == null) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 根据管理员传入的id查询对应的管理员
        Admin admin = adminService.selectAdminById(id);
        // 判断查询结果是否为空
        if (Objects.equals(adminService.selectAdminById(id).getId(), id)) {
            return new StatusAndDataFeedback(admin, "handle_success");
        }
        else {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
    }

    // 查找所有管理员
    @PostMapping("/selectAllAdmin")
    public StatusAndDataFeedback selectAllAdmin() {
        List<Admin> admins = adminService.selectAllAdmin();
        // 判断查询结果是否为空
        if (admins.isEmpty()) {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
        else {
            return new StatusAndDataFeedback(admins, "handle_success");
        }
    }

    // 修改管理员信息
    @PostMapping("/updateAdmin")
    public StatusAndDataFeedback updateAdmin(Admin admin) {
        // 判断前端传过来的参数是否为空
        if (admin == null) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 查找数据库中是否存在此管理员
        if (Objects.equals(adminService.selectAdminById(admin.getId()), null)) {
            return new StatusAndDataFeedback(admin, "data_not_exist");
        }
        else {
            // 如果管理员存在就更新数据
            adminService.updateAdmin(admin);
            return new StatusAndDataFeedback(admin, "handle_success");
        }
    }
}
