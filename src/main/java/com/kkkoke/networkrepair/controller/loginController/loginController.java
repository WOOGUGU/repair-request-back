package com.kkkoke.networkrepair.controller.loginController;

import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.pojo.Admin;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.service.AdminService;
import com.kkkoke.networkrepair.service.UserService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import com.kkkoke.networkrepair.util.MD5Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
public class loginController {
    private final UserService userService;
    private final AdminService adminService;

    public loginController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    // 处理登录请求
    @PostMapping("/handleLogin")

    public StatusAndDataFeedback handleLogin(@RequestBody JSONObject userJson) {
        String username = (String) userJson.get("username");
        String password = (String) userJson.get("password");
        // 查找数据库是否存在此用户
        if (Objects.equals(userService.selectUserByUsername(username), null) && Objects.equals(adminService.selectAdminByUsername(username), null)) {
            return new StatusAndDataFeedback(null, "wrong_user");
        }

        User user = userService.selectUserByUsername(username);
        Admin admin = adminService.selectAdminByUsername(username);
        // 判断登录密码是否正确
        if (Objects.equals(admin, null)) {
            // 与MD5加密后的字符串进行比较
            if (MD5Util.md5(user.getPassword()).equals(password)) {
                return new StatusAndDataFeedback(user, "login_success");
            }
            else {
                return new StatusAndDataFeedback(null, "wrong_password");
            }
        }
        else {
            // 与MD5加密后的字符串进行比较
            if (MD5Util.md5(admin.getPassword()).equals(password)) {
                return new StatusAndDataFeedback(admin, "login_success");
            }
            else {
                return new StatusAndDataFeedback(null, "wrong_password");
            }
        }
    }
}
