package com.kkkoke.networkrepair.controller;

import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.service.UserService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private StatusAndDataFeedback statusAndDataFeedback;

    // 添加用户
    @PostMapping("/addUser")
    public StatusAndDataFeedback addUser(String username, String password, String name) {
        if (username == "" || password == "" || name == "") {
            statusAndDataFeedback = new StatusAndDataFeedback(null, "Incomplete_data");
        }
        System.out.println("username:" + username);
        System.out.println("password:L" + password);
        System.out.println("name:" + name);
        User user = new User(username, password, name);
        // 调用service层添加用户
        userService.addUser(user);
        // 返回给前端添加的用户数据及处理的状态值
        statusAndDataFeedback = new StatusAndDataFeedback(user, "handle_success");

        return statusAndDataFeedback;
    }
}
