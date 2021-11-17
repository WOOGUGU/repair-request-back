package com.kkkoke.networkrepair.controller;

import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.service.UserService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 添加用户
    @PostMapping("/addUser")
    public StatusAndDataFeedback addUser(String username, String password, String name) {
        if (Objects.equals(username, "") || Objects.equals(password, "") || Objects.equals(name, "")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        User user = new User(username, password, name);
        // 调用service层添加用户
        userService.addUser(user);
        // 返回给前端添加的用户数据及处理的状态值
        return new StatusAndDataFeedback(user, "handle_success");
    }

    // 通过用户名删除用户
    @PostMapping("/deleteUser")
    public StatusAndDataFeedback deleteUser(Long id) {
        // 判断前端传过来的参数是否为空
        if (id == null) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 查询数据库，查看要删除的用户是否存在
        if (Objects.equals(userService.selectUserById(id).getId(), id)) {
            userService.deleteUser(id);
        }
        else {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }

        return new StatusAndDataFeedback(null, "handle_success");
    }

    // 通过用户名查找用户
    @PostMapping("/selectUserByUsername")
    public StatusAndDataFeedback selectUserByUsername(String username) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(username, "")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 根据用户名查找用户
        User user = userService.selectUserByUsername(username);
        // 判断查询结果是否为空
        if (Objects.equals(userService.selectUserByUsername(username).getUsername(), username)) {
            return new StatusAndDataFeedback(user, "handle_success");
        }
        else {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
    }

    // 通过id查找用户
    @PostMapping("/selectUserById")
    public StatusAndDataFeedback selectUserById(Long id) {
        // 判断前端传过来的参数是否为空
        if (id == null) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 根据用户传入的id查询对应的用户
        User user = userService.selectUserById(id);
        // 判断查询结果是否为空
        if (Objects.equals(userService.selectUserById(id).getId(), id)) {
            return new StatusAndDataFeedback(user, "handle_success");
        }
        else {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
    }

    // 查找所有用户
    @PostMapping("/selectAllUser")
    public StatusAndDataFeedback selectAllUser() {
        List<User> users = userService.selectAllUser();
        // 判断查询结果是否为空
        if (users.isEmpty()) {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
        else {
            return new StatusAndDataFeedback(users, "handle_success");
        }
    }

    // 修改用户信息
    @PostMapping("/updateUser")
    public StatusAndDataFeedback updateUser(User user) {
        System.out.println("==============ok==============");
        System.out.println(user);
        // 判断前端传过来的参数是否为空
        if (user == null) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        else {
            userService.updateUser(user);
            return new StatusAndDataFeedback(user, "handle_success");
        }
    }
}
