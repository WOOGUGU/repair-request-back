package com.kkkoke.networkrepair.util.token;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.kkkoke.networkrepair.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class AdminTokenVerifyImpl implements TokenVerify{
    @Autowired
    AdminService adminService;

    @Override
    public boolean verify(JSONObject Json, String token) {
        // 从json字符串中获取username和password
        String username = (String) Json.get("username");
        String password = (String) Json.get("password");
        // 解析token
        try {
            Map<String, Claim> jwt = JwtToken.verifyToken(token);
            // 验证token的正确性
            return Objects.equals(username, jwt.get("username").asString()) && Objects.equals(password, jwt.get("password").asString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
