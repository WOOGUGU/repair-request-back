package com.kkkoke.networkrepair.controller.pojoController;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.kkkoke.networkrepair.pojo.Admin;
import com.kkkoke.networkrepair.service.AdminService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import com.kkkoke.networkrepair.util.token.JwtToken;
import com.kkkoke.networkrepair.util.token.TokenVerify;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class AdminController {
    private final AdminService adminService;
    private final TokenVerify tokenVerify;

    public AdminController(AdminService adminService, @Qualifier("adminTokenVerifyImpl") TokenVerify tokenVerify) {
        this.adminService = adminService;
        this.tokenVerify = tokenVerify;
    }

    // 添加管理员
    @PostMapping("/addAdmin")
    public StatusAndDataFeedback addAdmin(@RequestBody JSONObject adminJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(adminJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        String username = (String) adminJson.get("username");
        String password = (String) adminJson.get("password");
        String name = (String) adminJson.get("name");
        String status = (String) adminJson.get("status");
        String token = (String) adminJson.get("token");
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，创建添加的admin对象
            Admin admin = new Admin(username, password, name, status);
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
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 通过用户名删除管理员
    @PostMapping("/deleteAdmin")
    public StatusAndDataFeedback deleteAdmin(@RequestBody JSONObject usernameJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(usernameJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong((String) usernameJson.get("id"));
        String token = (String) usernameJson.get("token");
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，查询数据库，查看要删除的管理员是否存在
            if (Objects.equals(adminService.selectAdminById(id), null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            }
            else {
                adminService.deleteAdmin(id);
                return new StatusAndDataFeedback(null, "handle_success");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 通过用户名查找管理员
    @PostMapping("/selectAdminByUsername")
    public StatusAndDataFeedback selectAdminByUsername(@RequestBody JSONObject usernameJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(usernameJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        String username = (String) usernameJson.get("username");
        String token = (String) usernameJson.get("token");
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，根据用户名查找管理员
            Admin admin = adminService.selectAdminByUsername(username);
            // 判断查询结果是否为空
            if (Objects.equals(adminService.selectAdminByUsername(username), null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            }
            else {
                return new StatusAndDataFeedback(admin, "handle_success");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 通过id查找管理员
    @PostMapping("/selectAdminById")
    public StatusAndDataFeedback selectAdminById(@RequestBody JSONObject idJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(idJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong(idJson.get("id").toString()); // 工单id
        String token = (String) idJson.get("token");
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，根据管理员传入的id查询对应的管理员
            Admin admin = adminService.selectAdminById(id);
            // 判断查询结果是否为空
            if (Objects.equals(adminService.selectAdminById(id).getId(), id)) {
                return new StatusAndDataFeedback(admin, "handle_success");
            }
            else {
                return new StatusAndDataFeedback(null, "data_not_exist");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 查找所有管理员
    @PostMapping("/selectAllAdmin")
    public StatusAndDataFeedback selectAllAdmin(@RequestBody JSONObject tokenJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(tokenJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取tokenJson中的数据
        String token = (String) tokenJson.get("token"); // 待验证的token
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，使用username查询该用户所发布的所有工单
            try {
                Map<String, Claim> jwt = JwtToken.verifyToken(token);
                String username = jwt.get("username").asString();
                List<Admin> admins = adminService.selectAllAdmin();
                // 判断查询结果是否为空
                if (admins.isEmpty()) {
                    return new StatusAndDataFeedback(null, "data_not_exist");
                }
                else {
                    return new StatusAndDataFeedback(admins, "handle_success");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new StatusAndDataFeedback(null, "exception_happen");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 修改管理员信息
    @PostMapping("/updateAdmin")
    public StatusAndDataFeedback updateAdmin(@RequestBody JSONObject adminJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(adminJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong((String) adminJson.get("id"));
        String username = (String) adminJson.get("username");
        String password = (String) adminJson.get("password");
        String name = (String) adminJson.get("name");
        String status = (String) adminJson.get("status");
        String token = (String) adminJson.get("token");
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，创建要修改的admin对象
            Admin admin = new Admin(username, password, name, status);
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
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }
}
